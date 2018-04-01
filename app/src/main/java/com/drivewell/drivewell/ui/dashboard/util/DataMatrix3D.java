package com.drivewell.drivewell.ui.dashboard.util;

public class DataMatrix3D {
    public final int COL_SIZE = 3;
    public final int ROW_SIZE = 3;
    public float[][] data = new float[3][];

    public DataMatrix3D() {
        for (int i = 0; i < 3; i++) {
            this.data[i] = new float[3];
        }
    }

    public Data3D multiplyData3D(Data3D d) {
        Data3D result = new Data3D(0.0f, 0.0f, 0.0f);
        result.f5x = ((this.data[0][0] * d.f5x) + (this.data[0][1] * d.f6y)) + (this.data[0][2] * d.f7z);
        result.f6y = ((this.data[1][0] * d.f5x) + (this.data[1][1] * d.f6y)) + (this.data[1][2] * d.f7z);
        result.f7z = ((this.data[2][0] * d.f5x) + (this.data[2][1] * d.f6y)) + (this.data[2][2] * d.f7z);
        return result;
    }

    public void setZero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.data[i][j] = 0.0f;
            }
        }
    }

    public void setIdentity() {
        setZero();
        for (int i = 0; i < Math.min(3, 3); i++) {
            this.data[i][i] = 1.0f;
        }
    }
}
