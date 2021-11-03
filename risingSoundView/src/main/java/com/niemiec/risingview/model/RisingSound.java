package com.niemiec.risingview.model;

import com.niemiec.risingview.view.RisingSoundValue;

public class RisingSound {
    private int time;

    public RisingSound() {
    }

    public boolean isOn() {
        return time != RisingSoundValue.NONE.getValue();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getTimeInMilliseconds() {
        return time * 1000;
    }
}
