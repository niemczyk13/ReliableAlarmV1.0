package com.niemiec.reliablealarmv10.activity.singleAlarm.launch.main;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;

public interface AlarmClockContractMVP {
    interface View {
        void startAlarm(SingleAlarm singleAlarm);
        void showAlarmClockWithNap(int hour, int minute);
        void showAlarmClockWithoutNap(int hour, int minute);
        void updateNotification(List<SingleAlarm> activeSingleAlarms);
    }

    interface Presenter {
        void initView(Long id);
        void onNapButtonClick();
        void onTurnOffButtonClick();
        void onPowerKeyClick();
    }
}
