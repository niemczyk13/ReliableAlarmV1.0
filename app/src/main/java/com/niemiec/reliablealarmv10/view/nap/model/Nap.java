package com.niemiec.reliablealarmv10.view.nap.model;

public class Nap {
    private int napTime;
    private int count = 0;

    public void setNapTime(int time) {
        this.napTime = time;
    }

    public int getNapTime() {
        return napTime;
    }

    public int getNextNapTime() {
        count++;
        return napTime;
    }

    public int getTheSumOfTheNapTimes() {
        return count * napTime;
    }

    public boolean isActive() {
        return napTime != 0;
    }

    public void resetNapsCount() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
