package com.drivewell.drivewell.coremodule.util;

public class Data2DCache extends DataCache<Data2D> {
    public Data2DCache(int l) {
        this.length = l;
    }

    public Data2D mean() {
        if (this.raw.size() == 0) {
            return new Data2D(0.0f, 0.0f);
        }
        double right_left = 0.0d;
        double acc_brake = 0.0d;
        for (int i = 0; i < this.raw.size(); i++) {
            right_left += (double) ((Data2D) this.raw.get(i)).right_left;
            acc_brake += (double) ((Data2D) this.raw.get(i)).acc_brake;
        }
        return new Data2D((float) (right_left / ((double) this.raw.size())), (float) (acc_brake / ((double) this.raw.size())));
    }

    public Data2D mean(double t) {
        if (this.raw.size() == 0) {
            return new Data2D(0.0f, 0.0f);
        }
        double right_left = 0.0d;
        double acc_brake = 0.0d;
        int i = 1;
        while (i <= this.raw.size() && Data2D.Ns2S(((Data2D) this.raw.getLast()).timestamp - ((Data2D) this.raw.get(this.raw.size() - i)).timestamp) < t) {
            right_left += (double) ((Data2D) this.raw.get(this.raw.size() - i)).right_left;
            acc_brake += (double) ((Data2D) this.raw.get(this.raw.size() - i)).acc_brake;
            i++;
        }
        return new Data2D((float) (right_left / ((double) (i - 1))), (float) (acc_brake / ((double) (i - 1))));
    }
}
