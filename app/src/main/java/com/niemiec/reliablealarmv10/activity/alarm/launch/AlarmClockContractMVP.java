package com.niemiec.reliablealarmv10.activity.alarm.launch;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

public interface AlarmClockContractMVP {
    interface View {
        void startAlarm(Alarm alarm);
        void showAlarmClockWithNap(int hour, int minute);
        void showAlarmClockWithoutNap(int hour, int minute);
    }

    interface Presenter {
        void initView(Long id);
        void onNapButtonClick();
        void onTurnOffButtonClick();
    }
}
