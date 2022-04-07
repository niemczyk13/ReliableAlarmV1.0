package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.logic;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchViewButtons;

public class SafeAlarmLaunchLogic {
    private SafeAlarmLaunch safeAlarmLaunch;
    private final SafeAlarmLaunchViewButtons buttons;

    public SafeAlarmLaunchLogic(SafeAlarmLaunchViewButtons buttons) {
        this.buttons = buttons;
    }

    public void initialize(SafeAlarmLaunch safeAlarmLaunch) {
        this.safeAlarmLaunch = safeAlarmLaunch;
        buttons.setSafeAlarmLaunch(safeAlarmLaunch);
        buttons.setSafeAlarmLaunchClickListener(this::onSafeAlarmButtonClick);
    }

    private void onSafeAlarmButtonClick() {
        safeAlarmLaunch = buttons.getSafeAlarmLaunch();
    }

    public SafeAlarmLaunch getSafeAlarmLaunch() {
        return safeAlarmLaunch;
    }
}
