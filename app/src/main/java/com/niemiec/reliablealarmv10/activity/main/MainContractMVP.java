package com.niemiec.reliablealarmv10.activity.main;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showAlarmList();
        void updateAlarmList();
        void showAlarmListForDeletion();
    }

    interface Presenter {
        void createDataBase();
        List<Alarm> getAllAlarms();
        void removeAlarms(List<Long> ids);
        void addAlarm();
        void updateAlarm(long id);
    }
}
