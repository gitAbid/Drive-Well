package com.drivewell.drivewell.ui;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.ui.dashboard.GForceMeterFragment;
import com.drivewell.drivewell.ui.dashboard.util.AccelerometerService;
import com.drivewell.drivewell.ui.dashboard.util.Data2D;
import com.drivewell.drivewell.ui.dashboard.util.Data3D;
import com.drivewell.drivewell.ui.dashboard.util.DataRecorder;
import com.drivewell.drivewell.ui.dashboard.util.GProcessor;
import com.drivewell.drivewell.ui.dashboard.util.SettingsWrapper;
import com.drivewell.drivewell.ui.profile.ProfileFragment;
import com.drivewell.drivewell.ui.roadcondition.RoadConditionMapFragment;
import com.drivewell.drivewell.ui.workplace.WorkPlaceFragment;

import java.lang.ref.WeakReference;

public class LandingActivity extends AppCompatActivity implements GForceMeterFragment.ActionHandler {

    private AccelerometerService.AccelerometerServiceBinder acc_service_binder;
    private ServiceConnection acc_service_connection;
    private AccelerometerServiceHandler acc_service_handler;
    private FragmentManager fragment_manager;
    private GForceMeterFragment g_force_fragment;
    private GProcessor g_processor;
    private GProcessorHandler g_processor_handler;
    private long last_refresh_timestamp = -1;
    private long last_sample_timestamp = -1;
    static Data2D processed_data;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:{
                    initializeFragment();
                    return true;
                }
                case R.id.navigation_roadcondition:
                    loadFragment(RoadConditionMapFragment.newInstance());
                    return true;
                case R.id.navigation_workplace:
                    loadFragment(WorkPlaceFragment.newInstance());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(ProfileFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        initializeSettings();
        initializeViewVariables();
        initializeViews();

        initializeData();
        initializeSensor();



    }

    private void loadFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer,fragment,fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(this.acc_service_connection);
    }

    private void initializeSettings() {
      SettingsWrapper.init(this);
    }

    private void initializeData() {
        DataRecorder.initialize(this);
    }

    private void initializeViewVariables() {
        this.fragment_manager = getFragmentManager();
    }

    private void initializeViews() {
        this.fragment_manager = getFragmentManager();
    }

    private void initializeFragment() {

        this.g_force_fragment = GForceMeterFragment.newInstance();
        loadFragment(this.g_force_fragment);
        this.g_force_fragment.action_handler = (GForceMeterFragment.ActionHandler) this;

    }

    @SuppressLint("WrongConstant")
    private void initializeSensor() {
        this.acc_service_handler = new AccelerometerServiceHandler(this);
        this.acc_service_connection = new AccelerometerServiceConnection();
        bindService(new Intent(this, AccelerometerService.class), this.acc_service_connection, 1);
        this.g_processor_handler = new GProcessorHandler(this);
        this.g_processor = new GProcessor(this.g_processor_handler);
    }




    public void GForceViewClicked() {
        this.g_processor.calibrate();
    }

    public static class AccelerometerServiceHandler extends Handler {
        private final WeakReference<LandingActivity> m_ref;

        public AccelerometerServiceHandler(LandingActivity a) {
            this.m_ref = new WeakReference(a);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (this.m_ref.get() != null || ((LandingActivity) this.m_ref.get()).g_processor != null) {
                Data3D data = (Data3D) msg.obj;
                ((LandingActivity) this.m_ref.get()).g_processor.process(data);
                if (((LandingActivity) this.m_ref.get()).last_refresh_timestamp < 0) {
                    ((LandingActivity) this.m_ref.get()).last_refresh_timestamp = data.timestamp;
                } else if (Data3D.Ns2S(data.timestamp - ((LandingActivity) this.m_ref.get()).last_refresh_timestamp) > ((double) (1.0f / SettingsWrapper.SENSOR_REFRESH_RATE))) {
                    ((LandingActivity) this.m_ref.get()).last_refresh_timestamp = data.timestamp;
                    if (((LandingActivity) this.m_ref.get()).g_force_fragment != null) {
                        Data2D processed_data = ((LandingActivity) this.m_ref.get()).g_processor.getProcessedData();
                        if (processed_data != null) {
                            ((LandingActivity) this.m_ref.get()).g_force_fragment.processData(processed_data);
                            if (((LandingActivity) this.m_ref.get()).last_sample_timestamp < 0) {
                                ((LandingActivity) this.m_ref.get()).last_sample_timestamp = data.timestamp;
                            } else if (Data3D.Ns2S(data.timestamp - ((LandingActivity) this.m_ref.get()).last_sample_timestamp) > ((double) (1.0f / SettingsWrapper.SENSOR_SAMPLE_RATE))) {
                                ((LandingActivity) this.m_ref.get()).last_sample_timestamp = data.timestamp;
                            }
                        }
                    }
                }
            }
        }
    }

    public static class GProcessorHandler extends Handler {
        private final WeakReference<LandingActivity> m_ref;

        public GProcessorHandler(LandingActivity a) {
            this.m_ref = new WeakReference(a);
        }

        @SuppressLint("WrongConstant")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            if (data.containsKey("screen_mode")) {
                Toast.makeText(this.m_ref.get(), data.getString("screen_mode"), 0).show();
                if (this.m_ref.get().g_force_fragment != null) {
                    ((LandingActivity) this.m_ref.get()).g_force_fragment.reset();
                }
            }
        }
    }

    class AccelerometerServiceConnection implements ServiceConnection {
        AccelerometerServiceConnection() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LandingActivity.this.acc_service_binder = (AccelerometerService.AccelerometerServiceBinder) iBinder;
            LandingActivity.this.acc_service_binder.getService().setHandler(LandingActivity.this.acc_service_handler);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            LandingActivity.this.acc_service_binder = null;
        }
    }

}
