package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchValue;

public class SafeAlarmLaunch {
    private int safeAlarmLaunchPercentage;

    public boolean isOn() {
        return safeAlarmLaunchPercentage != SafeAlarmLaunchValue.NONE.getValue();
    }

    public int getSafeAlarmLaunchPercentage() {
        return safeAlarmLaunchPercentage;
    }

    public void setSafeAlarmLaunchPercentage(int safeAlarmLaunchPercentage) {
        this.safeAlarmLaunchPercentage = safeAlarmLaunchPercentage;
    }

    public double getSafeAlarmLaunchPercentageInDouble() {
        return safeAlarmLaunchPercentage / 100;
    }
}
