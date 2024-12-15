package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchValue;

import lombok.Getter;
import lombok.Setter;

public class SafeAlarmLaunch {
    //@Setter
    //@Getter
    private int safeAlarmLaunchPercentage;
    //@Getter
    //@Setter
    private int timeBeforeAlarmSafeAlarmLaunchWork;

    public boolean isOn() {
        return safeAlarmLaunchPercentage != SafeAlarmLaunchValue.NONE.getValue();
    }

    public double getSafeAlarmLaunchPercentageInDouble() {
        return (double) safeAlarmLaunchPercentage / 100;
    }

    public int getSafeAlarmLaunchPercentage() {
        return safeAlarmLaunchPercentage;
    }

    public int getTimeBeforeAlarmSafeAlarmLaunchWork() {
        return timeBeforeAlarmSafeAlarmLaunchWork;
    }

    public void setSafeAlarmLaunchPercentage(int safeAlarmLaunchPercentage) {
        this.safeAlarmLaunchPercentage = safeAlarmLaunchPercentage;
    }

    public void setTimeBeforeAlarmSafeAlarmLaunchWork(int timeBeforeAlarmSafeAlarmLaunchWork) {
        this.timeBeforeAlarmSafeAlarmLaunchWork = timeBeforeAlarmSafeAlarmLaunchWork;
    }
}
