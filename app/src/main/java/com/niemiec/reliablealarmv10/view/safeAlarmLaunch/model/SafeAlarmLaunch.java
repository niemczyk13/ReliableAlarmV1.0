package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchValue;

import lombok.Getter;
import lombok.Setter;

public class SafeAlarmLaunch {
    @Setter
    @Getter
    private int safeAlarmLaunchPercentage;
    @Getter
    @Setter
    private int timeBeforeAlarmSafeAlarmLaunchWork;

    public boolean isOn() {
        return safeAlarmLaunchPercentage != SafeAlarmLaunchValue.NONE.getValue();
    }

    public double getSafeAlarmLaunchPercentageInDouble() {
        return (double) safeAlarmLaunchPercentage / 100;
    }
}
