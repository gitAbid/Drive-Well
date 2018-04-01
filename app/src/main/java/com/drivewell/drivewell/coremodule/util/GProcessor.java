package com.drivewell.drivewell.coremodule.util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GProcessor {
    public final int BUFFER_SIZE = 800;
    public final double CALIBRATE_WINDOW = 1.0d;
    private Data3D axis;
    private Data2DCache calibrated;
    private Data3D calibrated_g;
    private float g_magnitude;
    private Handler handler;
    private long last_timestamp;
    private DataMatrix3D matrix;
    private Data3DCache raw;
    private String[] screen_messages = new String[]{"Calibrated to default portrait mode", "Calibrated to left landscape mode", "Calibrated to right landscape mode", "Calibration failed, use default portrait mode"};
    private ScreenMode screen_mode;
    private float theta;
    private Data3D uncalibrated_g;

    private class CalibrateTask extends AsyncTask<Void, Void, Void> {
        private CalibrateTask() {
        }

        protected Void doInBackground(Void... voids) {
            Data3D virtual_g;
            GProcessor.this.uncalibrated_g = GProcessor.this.raw.mean(1.0d);
            GProcessor.this.g_magnitude = GProcessor.this.uncalibrated_g.magnitude();
            if (Math.abs(GProcessor.this.uncalibrated_g.f6y) <= Math.abs(GProcessor.this.uncalibrated_g.f5x) || Math.abs(GProcessor.this.uncalibrated_g.f6y) <= Math.abs(GProcessor.this.uncalibrated_g.f7z)) {
                if (Math.abs(GProcessor.this.uncalibrated_g.f5x) <= Math.abs(GProcessor.this.uncalibrated_g.f6y) || Math.abs(GProcessor.this.uncalibrated_g.f5x) <= Math.abs(GProcessor.this.uncalibrated_g.f7z)) {
                    if (Math.abs(GProcessor.this.uncalibrated_g.f7z) <= Math.abs(GProcessor.this.uncalibrated_g.f5x) || Math.abs(GProcessor.this.uncalibrated_g.f7z) <= Math.abs(GProcessor.this.uncalibrated_g.f6y)) {
                        GProcessor.this.screen_mode = ScreenMode.UNDEFINED;
                    } else if (GProcessor.this.uncalibrated_g.f7z >= 0.0f) {
                        GProcessor.this.screen_mode = ScreenMode.UNDEFINED;
                    } else if (Math.abs(GProcessor.this.uncalibrated_g.f6y) > Math.abs(GProcessor.this.uncalibrated_g.f5x)) {
                        if (GProcessor.this.uncalibrated_g.f6y < 0.0f) {
                            GProcessor.this.screen_mode = ScreenMode.PORTRAIT_DEFAULT;
                        } else {
                            GProcessor.this.screen_mode = ScreenMode.UNDEFINED;
                        }
                    } else if (GProcessor.this.uncalibrated_g.f5x < 0.0f) {
                        GProcessor.this.screen_mode = ScreenMode.LANDSCAPE_LEFT;
                    } else {
                        GProcessor.this.screen_mode = ScreenMode.LANDSCAPE_RIGHT;
                    }
                } else if (GProcessor.this.uncalibrated_g.f5x < 0.0f) {
                    GProcessor.this.screen_mode = ScreenMode.LANDSCAPE_LEFT;
                } else {
                    GProcessor.this.screen_mode = ScreenMode.LANDSCAPE_RIGHT;
                }
            } else if (GProcessor.this.uncalibrated_g.f6y < 0.0f) {
                GProcessor.this.screen_mode = ScreenMode.PORTRAIT_DEFAULT;
            } else {
                GProcessor.this.screen_mode = ScreenMode.UNDEFINED;
            }
            if (GProcessor.this.screen_mode == ScreenMode.PORTRAIT_DEFAULT) {
                virtual_g = new Data3D(0.0f, -1.0f, 0.0f);
            } else if (GProcessor.this.screen_mode == ScreenMode.LANDSCAPE_LEFT) {
                virtual_g = new Data3D(-1.0f, 0.0f, 0.0f);
            } else if (GProcessor.this.screen_mode == ScreenMode.LANDSCAPE_RIGHT) {
                virtual_g = new Data3D(1.0f, 0.0f, 0.0f);
            } else {
                virtual_g = new Data3D(0.0f, -1.0f, 0.0f);
            }
            GProcessor.this.axis = Data3D.cross(GProcessor.this.uncalibrated_g, virtual_g);
            GProcessor.this.theta = (float) Math.asin((double) ((GProcessor.this.axis.magnitude() / GProcessor.this.uncalibrated_g.magnitude()) / virtual_g.magnitude()));
            GProcessor.this.axis = GProcessor.this.axis.normalize();
            GProcessor.this.matrix.data[0][0] = (float) (Math.cos((double) GProcessor.this.theta) + (((double) (GProcessor.this.axis.f5x * GProcessor.this.axis.f5x)) * (1.0d - Math.cos((double) GProcessor.this.theta))));
            GProcessor.this.matrix.data[0][1] = (float) ((((double) (GProcessor.this.axis.f5x * GProcessor.this.axis.f6y)) * (1.0d - Math.cos((double) GProcessor.this.theta))) - (((double) GProcessor.this.axis.f7z) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[0][2] = (float) ((((double) (GProcessor.this.axis.f5x * GProcessor.this.axis.f7z)) * (1.0d - Math.cos((double) GProcessor.this.theta))) + (((double) GProcessor.this.axis.f6y) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[1][0] = (float) ((((double) (GProcessor.this.axis.f6y * GProcessor.this.axis.f5x)) * (1.0d - Math.cos((double) GProcessor.this.theta))) + (((double) GProcessor.this.axis.f7z) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[1][1] = (float) (Math.cos((double) GProcessor.this.theta) + (((double) (GProcessor.this.axis.f6y * GProcessor.this.axis.f6y)) * (1.0d - Math.cos((double) GProcessor.this.theta))));
            GProcessor.this.matrix.data[1][2] = (float) ((((double) (GProcessor.this.axis.f6y * GProcessor.this.axis.f7z)) * (1.0d - Math.cos((double) GProcessor.this.theta))) - (((double) GProcessor.this.axis.f5x) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[2][0] = (float) ((((double) (GProcessor.this.axis.f7z * GProcessor.this.axis.f5x)) * (1.0d - Math.cos((double) GProcessor.this.theta))) - (((double) GProcessor.this.axis.f6y) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[2][1] = (float) ((((double) (GProcessor.this.axis.f7z * GProcessor.this.axis.f6y)) * (1.0d - Math.cos((double) GProcessor.this.theta))) + (((double) GProcessor.this.axis.f5x) * Math.sin((double) GProcessor.this.theta)));
            GProcessor.this.matrix.data[2][2] = (float) (Math.cos((double) GProcessor.this.theta) + (((double) (GProcessor.this.axis.f7z * GProcessor.this.axis.f7z)) * (1.0d - Math.cos((double) GProcessor.this.theta))));
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("screen_mode", GProcessor.this.screen_messages[GProcessor.this.screen_mode.ordinal()]);
            msg.setData(data);
            GProcessor.this.handler.sendMessage(msg);
            GProcessor.this.raw.clear();
            GProcessor.this.calibrated.clear();
            return null;
        }
    }

    private class ProcessTask extends AsyncTask<Data3D, Void, Void> {
        private ProcessTask() {
        }

        protected Void doInBackground(Data3D... data) {
            if (data.length > 0) {
                GProcessor.this.raw.addData(data[0]);
                GProcessor.this.calibrated_g = GProcessor.this.matrix.multiplyData3D(data[0]);
                Data2D calibrated_data = new Data2D(0.0f, 0.0f, data[0].timestamp);
                if (GProcessor.this.screen_mode == ScreenMode.PORTRAIT_DEFAULT) {
                    calibrated_data.right_left = (-GProcessor.this.calibrated_g.f5x) / GProcessor.this.g_magnitude;
                    calibrated_data.acc_brake = GProcessor.this.calibrated_g.f7z / GProcessor.this.g_magnitude;
                } else if (GProcessor.this.screen_mode == ScreenMode.LANDSCAPE_LEFT) {
                    calibrated_data.right_left = GProcessor.this.calibrated_g.f6y / GProcessor.this.g_magnitude;
                    calibrated_data.acc_brake = GProcessor.this.calibrated_g.f7z / GProcessor.this.g_magnitude;
                } else if (GProcessor.this.screen_mode == ScreenMode.LANDSCAPE_RIGHT) {
                    calibrated_data.right_left = (-GProcessor.this.calibrated_g.f6y) / GProcessor.this.g_magnitude;
                    calibrated_data.acc_brake = GProcessor.this.calibrated_g.f7z / GProcessor.this.g_magnitude;
                } else {
                    calibrated_data.right_left = (-GProcessor.this.calibrated_g.f5x) / GProcessor.this.g_magnitude;
                    calibrated_data.acc_brake = GProcessor.this.calibrated_g.f7z / GProcessor.this.g_magnitude;
                }
                if (GProcessor.this.last_timestamp < 0) {
                    GProcessor.this.last_timestamp = data[0].timestamp;
                } else if (GProcessor.this.calibrated.getLength() > 0) {
                    double interval = Data2D.Ns2S(data[0].timestamp - GProcessor.this.last_timestamp);
                    double alpha = ((6.283185307179586d * interval) * ((double) SettingsWrapper.SENSOR_COF)) / (((6.283185307179586d * interval) * ((double) SettingsWrapper.SENSOR_COF)) + 1.0d);
                    calibrated_data.right_left = (float) ((((double) calibrated_data.right_left) * alpha) + ((1.0d - alpha) * ((double) ((Data2D) GProcessor.this.calibrated.getLast()).right_left)));
                    calibrated_data.acc_brake = (float) ((((double) calibrated_data.acc_brake) * alpha) + ((1.0d - alpha) * ((double) ((Data2D) GProcessor.this.calibrated.getLast()).acc_brake)));
                }
                GProcessor.this.calibrated.addData(calibrated_data);
                GProcessor.this.last_timestamp = data[0].timestamp;
            }
            return null;
        }
    }

    public enum ScreenMode {
        PORTRAIT_DEFAULT,
        LANDSCAPE_LEFT,
        LANDSCAPE_RIGHT,
        UNDEFINED
    }

    public GProcessor(Handler h) {
        this.handler = h;
        this.raw = new Data3DCache(800);
        this.calibrated = new Data2DCache(800);
        this.screen_mode = ScreenMode.UNDEFINED;
        this.matrix = new DataMatrix3D();
        this.matrix.setIdentity();
        this.g_magnitude = 9.8f;
        this.last_timestamp = -1;
    }

    public void process(Data3D data) {
        new ProcessTask().execute(new Data3D[]{data});
    }

    public void calibrate() {
        new CalibrateTask().execute(new Void[0]);
    }

    public Data2D getProcessedData() {
        if (this.calibrated != null) {
            return (Data2D) this.calibrated.getLast();
        }
        return null;
    }
}
