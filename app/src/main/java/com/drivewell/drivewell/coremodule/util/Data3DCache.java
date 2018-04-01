package com.drivewell.drivewell.coremodule.util;

public class Data3DCache extends DataCache<Data3D> {
    public Data3DCache(int l) {
        this.length = l;
    }

    public Data3D mean() {
        if (this.raw.size() == 0) {
            return new Data3D(0.0f, 0.0f, 0.0f);
        }
        double x = 0.0d;
        double y = 0.0d;
        double z = 0.0d;
        for (int i = 0; i < this.raw.size(); i++) {
            x += (double) ((Data3D) this.raw.get(i)).f5x;
            y += (double) ((Data3D) this.raw.get(i)).f6y;
            z += (double) ((Data3D) this.raw.get(i)).f7z;
        }
        return new Data3D((float) (x / ((double) this.raw.size())), (float) (y / ((double) this.raw.size())), (float) (z / ((double) this.raw.size())));
    }

    public Data3D mean(double t) {
        if (this.raw.size() == 0) {
            return new Data3D(0.0f, 0.0f, 0.0f);
        }
        double x = 0.0d;
        double y = 0.0d;
        double z = 0.0d;
        int i = 1;
        while (i <= this.raw.size() && Data3D.Ns2S(((Data3D) this.raw.getLast()).timestamp - ((Data3D) this.raw.get(this.raw.size() - i)).timestamp) < t) {
            x += (double) ((Data3D) this.raw.get(this.raw.size() - i)).f5x;
            y += (double) ((Data3D) this.raw.get(this.raw.size() - i)).f6y;
            z += (double) ((Data3D) this.raw.get(this.raw.size() - i)).f7z;
            i++;
        }
        return new Data3D((float) (x / ((double) (i - 1))), (float) (y / ((double) (i - 1))), (float) (z / ((double) (i - 1))));
    }
}
