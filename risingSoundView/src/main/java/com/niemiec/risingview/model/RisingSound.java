package com.niemiec.risingview.model;

import com.niemiec.risingview.view.RisingSoundValue;

public class RisingSound {
    private int risingSoundTime;

    public RisingSound() {
    }

    public boolean isOn() {
        return risingSoundTime != RisingSoundValue.NONE.getValue();
    }

    public int getRisingSoundTime() {
        return risingSoundTime;
    }

    public void setRisingSoundTime(int risingSoundTime) {
        this.risingSoundTime = risingSoundTime;
    }

    public long getTimeInMilliseconds() {
        return risingSoundTime * 1000;
    }
}
