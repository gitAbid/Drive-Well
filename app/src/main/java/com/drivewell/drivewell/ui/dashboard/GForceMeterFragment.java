package com.drivewell.drivewell.ui.dashboard;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.coremodule.EvaluationModule;
import com.drivewell.drivewell.ui.LoginActivity;
import com.drivewell.drivewell.ui.dashboard.util.Data2D;
import com.drivewell.drivewell.ui.dashboard.util.SettingsWrapper;
import com.drivewell.drivewell.ui.dashboard.view.GForceMeterView;
import com.drivewell.drivewell.ui.dashboard.view.RobotoTextView;
import com.google.firebase.auth.FirebaseAuth;


public class GForceMeterFragment extends Fragment {

    private static GForceMeterFragment instance;
    private static boolean is_fragment_ready = false;
    public ActionHandler action_handler;
    private RobotoTextView brake_acc;
    private GForceMeterView g_force_meter;

    private float m_max_acc = 1.0E-6f;
    private float m_max_brake = -1.0E-6f;
    private float m_max_left = -1.0E-6f;
    private float m_max_right = 1.0E-6f;
    private View m_view;
    private RobotoTextView max_left;
    private RobotoTextView max_right;

    private TextView mFront, mBehind, mLeft, mRight, mRemarks, mPoints, mDrivingStatusLR,mDrivingStatusAB;
    private ImageView mDrivingDirectionLR;
    private ImageView mDrivingDirectionAB;
    private RobotoTextView mTurningPoints;
    private RobotoTextView mAccPoints;
    private RobotoTextView mBrakePoints;


    private Button mSignOut;

    public static GForceMeterFragment getInstance() {
        return instance == null ? new GForceMeterFragment() : instance;
    }

    public static GForceMeterFragment newInstance() {

        Bundle args = new Bundle();

        GForceMeterFragment fragment = new GForceMeterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_view = inflater.inflate(R.layout.layout_main_dashboard, container, false);
        mSignOut = m_view.findViewById(R.id.btSignout);

        mSignOut.setOnClickListener(e -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        initializeViews(m_view);
        this.is_fragment_ready = true;
        return m_view;
    }

    public void setPoints(String s) {
        mPoints.setText("Points " + s);
    }
    public void setAccPoints(String s) {
        mAccPoints.setText(s+" pt");
    }
    public void setBrakePoints(String s) {
        mBrakePoints.setText(s+" pt");
    }
    public void setTurnPoints(String s) {
        mTurningPoints.setText(s+" pt");
    }

    private void initializeViews(View v) {
        this.g_force_meter = (GForceMeterView) v.findViewById(R.id.g_force_view_meter);
        this.g_force_meter.resetMaxLength((int) (SettingsWrapper.SENSOR_REFRESH_RATE * SettingsWrapper.G_FORCE_TRAIL_LENGTH));
        this.g_force_meter.setMaxG(SettingsWrapper.G_FORCE_MAX_G);
        this.g_force_meter.setOnClickListener(new GForceViewClick());
        this.mBrakePoints = (RobotoTextView) v.findViewById(R.id.g_force_view_brake);
        /*this.brake_acc = (RobotoTextView) v.findViewById(R.id.g_force_view_brake_acc);*/
        this.mAccPoints = (RobotoTextView) v.findViewById(R.id.g_force_view_acc);
        /*this.max_left = (RobotoTextView) v.findViewById(R.id.g_force_view_left);*/
        this.mTurningPoints = (RobotoTextView) v.findViewById(R.id.g_force_view_left_right);
        /*this.max_right = (RobotoTextView) v.findViewById(R.id.g_force_view_right);*/
        this.mLeft = v.findViewById(R.id.tvLeft);
        this.mRight = v.findViewById(R.id.tvRight);
        this.mFront = v.findViewById(R.id.tvFront);
        this.mBehind = v.findViewById(R.id.tvBehind);
        this.mPoints = v.findViewById(R.id.tvPoints);
        this.mDrivingStatusLR=v.findViewById(R.id.tvDrivingStatusLR);
        this.mDrivingStatusAB=v.findViewById(R.id.tvDrivingStatusAB);
        this.mDrivingDirectionLR=v.findViewById(R.id.ivDrivingDirectionLR);
        this.mDrivingDirectionAB=v.findViewById(R.id.ivDrivingDirectionAB);
    }

    public void reset() {
        if (this.is_fragment_ready) {
            this.m_max_left = 0.0f;
            this.m_max_right = 0.0f;
            this.m_max_brake = 0.0f;
            this.m_max_acc = 0.0f;
           // this.max_left.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_left)}));
           // this.max_right.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_right)}));
//            this.mBrakePoints.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_brake)}));
//            this.mAccPoints.setText(String.format("%.2f", new Object[]{Float.valueOf(this.m_max_acc)}));
            this.g_force_meter.resetMaxLength((int) (SettingsWrapper.SENSOR_REFRESH_RATE * SettingsWrapper.G_FORCE_TRAIL_LENGTH));
            this.g_force_meter.setMaxG(SettingsWrapper.G_FORCE_MAX_G);
            this.g_force_meter.resetPath();
            clearDirection();
        }
    }

    public void processData(Data2D data) {
        if (this.is_fragment_ready) {

            Log.d("DATA/Fragment", data.toString());

            try {
                this.g_force_meter.setGForce(data);

            } catch (Exception e) {
                e.printStackTrace();
            }

            clearDirection();
            if (data.right_left < 0.0f) {

                this.mLeft.setText("◀");
                this.mDrivingStatusLR.setText("Turning Left\n"+String.format("%.2f", new Object[]{Float.valueOf(-data.right_left)}) + " L");
                this.mDrivingDirectionLR.setImageResource(R.drawable.ic_subdirectory_arrow_left_black_24dp);
                /*if (data.right_left < this.m_max_left) {
                    this.m_max_left = data.right_left;
                    this.max_left.setText("Left\n" + String.format("%.2f", new Object[]{Float.valueOf(-this.m_max_left)}));
                }*/
            } else {
                this.mRight.setText("▶");
                this.mDrivingStatusLR.setText("Turning Right\n"+String.format("%.2f", new Object[]{Float.valueOf(data.right_left)}) + " R");
                this.mDrivingDirectionLR.setImageResource(R.drawable.ic_subdirectory_arrow_right_black_24dp);
               /* if (data.right_left > this.m_max_right) {
                    this.m_max_right = data.right_left;
                    this.max_right.setText("Right\n" + String.format("%.2f", new Object[]{Float.valueOf(this.m_max_right)}));
                }*/
            }

            if (data.acc_brake < 0.0f) {
                this.mBehind.setText("▼");
                this.mDrivingStatusAB.setText("Braking\n"+String.format("%.2f", new Object[]{Float.valueOf(-data.acc_brake)}) + " B");
                this.mDrivingDirectionAB.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
               /* if (data.acc_brake < this.m_max_brake) {
                    this.m_max_brake = data.acc_brake;
                    this.mBrakePoints.setText("Brake\n" + String.format("%.2f", new Object[]{Float.valueOf(-this.m_max_brake)}));
                    return;
                }*/
                return;
            } else {
                this.mFront.setText("▲");
                this.mDrivingStatusAB.setText("Accelerate\n"+String.format("%.2f", new Object[]{Float.valueOf(data.acc_brake)}) + " A");
                this.mDrivingDirectionAB.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
               /* if (data.acc_brake > this.m_max_acc) {
                    this.m_max_acc = data.acc_brake;
                    this.mAccPoints.setText("Acc\n" + String.format("%.2f", new Object[]{Float.valueOf(this.m_max_acc)}));
                }*/
            }
        }
    }

    void clearDirection() {
        mBehind.setText("");
        mFront.setText("");
        mLeft.setText("");
        mRight.setText("");
    }

    public interface ActionHandler {
        void GForceViewClicked();
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

}




