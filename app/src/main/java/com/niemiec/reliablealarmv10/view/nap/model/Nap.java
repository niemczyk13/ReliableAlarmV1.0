package com.niemiec.reliablealarmv10.view.nap.model;

public class Nap {
    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public long getTimeInMilliseconds() {
        //TODO
        return time / 1000;
    }
}
