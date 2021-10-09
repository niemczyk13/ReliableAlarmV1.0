package com.niemiec.risingview.model;

import com.niemiec.risingview.view.RisingSoundValue;

public class RisingSound {
    private final int time;

    public RisingSound(int time) {
        this.time = time;
    }

    public boolean isOn() {
        return time != RisingSoundValue.NONE.getValue();
    }

    public int getTime() {
        return time;
    }

}
