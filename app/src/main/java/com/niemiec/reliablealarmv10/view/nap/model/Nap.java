package com.niemiec.reliablealarmv10.view.nap.model;

public class Nap {
    private int napTime;

    public void setNapTime(int time) {
        this.napTime = time;
    }

    public int getNapTime() {
        return napTime;
    }

    public long getTimeInMilliseconds() {
        //TODO
        return napTime / 1000;
    }

    public boolean isActive() {
        return napTime != 0;
    }
}
