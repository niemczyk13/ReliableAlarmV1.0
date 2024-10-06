package com.niemiec.reliablealarmv10.activity.alarm.launch.main;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

public interface AlarmClockContractMVP {
    interface View {
        void startAlarm(SingleAlarmEntity singleAlarm);
        void showAlarmClockWithNap(int hour, int minute);
        void showAlarmClockWithoutNap(int hour, int minute);
        void updateNotification(List<SingleAlarmEntity> activeSingleAlarms);
    }

    interface Presenter {
        void initView(Long id);
        void onNapButtonClick();
        void onTurnOffButtonClick();
        void onPowerKeyClick();
    }
}
