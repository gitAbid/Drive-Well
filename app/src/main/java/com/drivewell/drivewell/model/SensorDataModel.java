package com.drivewell.drivewell.model;

public class SensorDataModel {
    
    private float X;
    private float Y;
    private float Z;

    public SensorDataModel(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getZ() {
        return Z;
    }

    public void setZ(float z) {
        Z = z;
    }
}