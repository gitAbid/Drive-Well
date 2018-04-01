package com.drivewell.drivewell.coremodule.util;

public class Data2D {
    public float acc_brake;
    public float right_left;
    public long timestamp;

    public enum MODE {
        PORTRAIT_NORMAL,
        PORTRAIT_INVERSE,
        LANDSCAPE_LEFT,
        LANDSCAPE_RIGHT,
        FLAT
    }

    public Data2D(float lr, float ab, long ts) {
        this.right_left = lr;
        this.acc_brake = ab;
        this.timestamp = ts;
    }

    public Data2D(float lr, float ab) {
        this.right_left = lr;
        this.acc_brake = ab;
        this.timestamp = 0;
    }

    public Data2D(Data3D data, MODE mode, Data3D baseline, long ts) {
        switch (mode) {
            case PORTRAIT_NORMAL:
                this.right_left = data.f5x - baseline.f5x;
                this.acc_brake = data.f7z - baseline.f7z;
                break;
            case LANDSCAPE_LEFT:
                this.right_left = -(data.f6y - baseline.f6y);
                this.acc_brake = data.f7z - baseline.f7z;
                break;
            case LANDSCAPE_RIGHT:
                this.right_left = data.f6y - baseline.f6y;
                this.acc_brake = data.f7z - baseline.f7z;
                break;
            default:
                this.right_left = 0.0f;
                this.acc_brake = 0.0f;
                break;
        }
        this.timestamp = ts;
    }

    @Override
    public String toString() {
        return "Data2D{" +
                "acc_brake=" + acc_brake +
                ", right_left=" + right_left +
                ", timestamp=" + timestamp +
                '}';
    }

    public static double Ns2S(long t) {
        return ((double) t) * 1.0E-9d;
    }
}
