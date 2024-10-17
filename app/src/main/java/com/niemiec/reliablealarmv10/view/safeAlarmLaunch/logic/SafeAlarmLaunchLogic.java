package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.logic;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchViewButtons;

import lombok.Getter;

public class SafeAlarmLaunchLogic {
    @Getter
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

}
