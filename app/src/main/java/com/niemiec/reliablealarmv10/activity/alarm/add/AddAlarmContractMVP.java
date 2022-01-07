package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public interface AddAlarmContractMVP {
    interface View {
        void showAlarm(Alarm alarm);
        Alarm getAlarm();
        void goBackToPreviousActivity();
        void startAlarm(Alarm alarm);
        void stopAlarm(Alarm alarm);
        void updateNotification(List<Alarm> activeAlarms);

    }

    interface Presenter {
        void downloadAlarm(Bundle bundle);
        void saveAlarm();
    }
}
