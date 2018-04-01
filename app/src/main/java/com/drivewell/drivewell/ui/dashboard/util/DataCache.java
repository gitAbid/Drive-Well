package com.drivewell.drivewell.ui.dashboard.util;
import java.util.LinkedList;

public class DataCache<E> {
    public int length;
    public LinkedList<E> raw;

    public DataCache() {
        this.length = 0;
        this.raw = new LinkedList();
        this.length = 0;
    }

    public DataCache(int l) {
        this.length = 0;
        this.raw = new LinkedList();
        this.length = l;
    }

    public void addData(E data) {
        if (this.length <= 0) {
            this.raw.add(data);
        } else if (this.raw.size() < this.length) {
            this.raw.add(data);
        } else {
            while (this.raw.size() > this.length - 1) {
                this.raw.removeFirst();
            }
            this.raw.add(data);
        }
    }

    public int getLength() {
        if (this.raw != null) {
            return this.raw.size();
        }
        return 0;
    }

    public void setLength(int l) {
        this.length = l;
        clear();
    }

    public E get(int i) {
        if (this.raw != null) {
            return this.raw.get(i);
        }
        return null;
    }

    public E getLast() {
        if (this.raw == null || this.raw.isEmpty()) {
            return null;
        }
        return this.raw.getLast();
    }

    public E getFirst() {
        if (this.raw != null) {
            return this.raw.getFirst();
        }
        return null;
    }

    public void clear() {
        if (this.raw != null) {
            this.raw.clear();
        }
    }
}
