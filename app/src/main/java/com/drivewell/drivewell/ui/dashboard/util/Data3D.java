package com.drivewell.drivewell.ui.dashboard.util;
public class Data3D {
    public long timestamp;
    public float f5x;
    public float f6y;
    public float f7z;

    public Data3D(float x_raw, float y_raw, float z_raw, long ts) {
        this.f5x = x_raw;
        this.f6y = y_raw;
        this.f7z = z_raw;
        this.timestamp = ts;
    }

    public Data3D(float x_raw, float y_raw, float z_raw) {
        this.f5x = x_raw;
        this.f6y = y_raw;
        this.f7z = z_raw;
        this.timestamp = 0;
    }

    public float magnitude() {
        return (float) Math.sqrt((double) (((this.f5x * this.f5x) + (this.f6y * this.f6y)) + (this.f7z * this.f7z)));
    }

    public Data3D normalize() {
        float mag = magnitude();
        return new Data3D(this.f5x / mag, this.f6y / mag, this.f7z / mag);
    }

    public static float dot(Data3D a, Data3D b) {
        return ((a.f5x * b.f5x) + (a.f6y * b.f6y)) + (a.f7z * b.f7z);
    }

    public static Data3D cross(Data3D a, Data3D b) {
        Data3D result = new Data3D(0.0f, 0.0f, 0.0f);
        result.f5x = (a.f6y * b.f7z) - (a.f7z * b.f6y);
        result.f6y = (a.f7z * b.f5x) - (a.f5x * b.f7z);
        result.f7z = (a.f5x * b.f6y) - (a.f6y * b.f5x);
        return result;
    }

    public static double Ns2S(long t) {
        return ((double) t) * 1.0E-9d;
    }
}
