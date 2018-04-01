package com.drivewell.drivewell.coremodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.coremodule.util.Data2D;
import com.drivewell.drivewell.coremodule.util.SettingsWrapper;
import com.drivewell.drivewell.coremodule.view.GForceMeterView;
import com.drivewell.drivewell.coremodule.view.RobotoTextView;



public class GForceMeterFragment extends Fragment {

    private static GForceMeterFragment instance;
    public ActionHandler action_handler;
    private RobotoTextView brake_acc;
    private GForceMeterView g_force_meter;
    private static boolean is_fragment_ready = false;
    private RobotoTextView left_right;
    private float m_max_acc = 1.0E-6f;
    private float m_max_brake = -1.0E-6f;
    private float m_max_left = -1.0E-6f;
    private float m_max_right = 1.0E-6f;
    private View m_view;
    private RobotoTextView max_acc;
    private RobotoTextView max_brake;
    private RobotoTextView max_left;
    private RobotoTextView max_right;

    public static GForceMeterFragment getInstance() {
        return instance==null?new GForceMeterFragment():instance;
    }

    class GForceViewClick implements View.OnClickListener {
        GForceViewClick() {
        }

        public void onClick(View view) {
            if (GForceMeterFragment.this.action_handler != null) {
                GForceMeterFragment.this.action_handler.GForceViewClicked();
            }
        }
    }

    public interface ActionHandler {
        void GForceViewClicked();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_view = inflater.inflate(R.layout.fragment_gforce_meter, container, false);
        initializeViews(m_view);
        this.is_fragment_ready = true;
        return m_view;
    }

    public static GForceMeterFragment newInstance() {

        Bundle args = new Bundle();

        GForceMeterFragment fragment = new GForceMeterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initializeViews(View v) {
        this.g_force_meter = (GForceMeterView) v.findViewById(R.id.g_force_view_meter);
        this.g_force_meter.resetMaxLength((int) (SettingsWrapper.SENSOR_REFRESH_RATE * SettingsWrapper.G_FORCE_TRAIL_LENGTH));
        this.g_force_meter.setMaxG(SettingsWrapper.G_FORCE_MAX_G);
        this.g_force_meter.setOnClickListener(new GForceViewClick());
        this.max_brake = (RobotoTextView) v.findViewById(R.id.g_force_view_brake);
        this.brake_acc = (RobotoTextView) v.findViewById(R.id.g_force_view_brake_acc);
        this.max_acc = (RobotoTextView) v.findViewById(R.id.g_force_view_acc);
        this.max_left = (RobotoTextView) v.findViewById(R.id.g_force_view_left);
        this.left_right = (RobotoTextView) v.findViewById(R.id.g_force_view_left_right);
        this.max_right = (RobotoTextView) v.findViewById(R.id.g_force_view_right);
    }

    public void reset() {
        if (this.is_fragment_ready) {
            this.m_max_left = 0.0f;
            this.m_max_right = 0.0f;
            this.m_max_brake = 0.0f;
            this.m_max_acc = 0.0f;
            this.max_left.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_left)}));
            this.max_right.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_right)}));
            this.max_brake.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_brake)}));
            this.max_acc.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_acc)}));
            this.g_force_meter.resetMaxLength((int) (SettingsWrapper.SENSOR_REFRESH_RATE * SettingsWrapper.G_FORCE_TRAIL_LENGTH));
            this.g_force_meter.setMaxG(SettingsWrapper.G_FORCE_MAX_G);
            this.g_force_meter.resetPath();
        }
    }

    public void processData(Data2D data) {
        if (this.is_fragment_ready) {
            Log.d("DATA/Fragment",data.toString());
            this.g_force_meter.setGForce(data);
            if (data.right_left < 0.0f) {
                this.left_right.setText(String.format("%.2f", new Object[]{Float.valueOf(-data.right_left)}) + " L");
                if (data.right_left < this.m_max_left) {
                    this.m_max_left = data.right_left;
                    this.max_left.setText(String.format("%.2f", new Object[]{Float.valueOf(-this.m_max_left)}));
                }
            } else {
                this.left_right.setText(String.format("%.2f", new Object[]{Float.valueOf(data.right_left)}) + " R");
                if (data.right_left > this.m_max_right) {
                    this.m_max_right = data.right_left;
                    this.max_right.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_right)}));
                }
            }
            if (data.acc_brake < 0.0f) {
                this.brake_acc.setText(String.format("%.2f", new Object[]{Float.valueOf(-data.acc_brake)}) + " B");
                if (data.acc_brake < this.m_max_brake) {
                    this.m_max_brake = data.acc_brake;
                    this.max_brake.setText(String.format("%.2f", new Object[]{Float.valueOf(-this.m_max_brake)}));
                    return;
                }
                return;
            }
            this.brake_acc.setText(String.format("%.2f", new Object[]{Float.valueOf(data.acc_brake)}) + " A");
            if (data.acc_brake > this.m_max_acc) {
                this.m_max_acc = data.acc_brake;
                this.max_acc.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_acc)}));
            }
        }
    }
}
