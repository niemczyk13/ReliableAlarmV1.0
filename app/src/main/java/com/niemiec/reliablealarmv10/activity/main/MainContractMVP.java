package com.niemiec.reliablealarmv10.activity.main;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showAlarmList(List<Alarm> alarms);
        void updateAlarmList();
        void showAlarmListForDeletion();
        void showMainAlarmList();
    }

    interface Presenter {
        void initView();

        List<Alarm> getAllAlarms();
        void removeAlarms(List<Long> ids);
        void addAlarm();
        void updateAlarm(long id);
    }
}
