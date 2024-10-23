package com.niemiec.reliablealarmv10.activity.alarm.launch.main;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

public interface AlarmClockContractMVP {
    interface View {
        void startAlarm(SingleAlarmModel singleAlarm);
        void showAlarmClockWithNap(int hour, int minute);
        void showAlarmClockWithoutNap(int hour, int minute);
        void updateNotification(List<SingleAlarmModel> activeSingleAlarms);
    }

    interface Presenter {
        void initView(Long id);
        void onNapButtonClick();
        void onTurnOffButtonClick();
        void onPowerKeyClick();
    }
}
