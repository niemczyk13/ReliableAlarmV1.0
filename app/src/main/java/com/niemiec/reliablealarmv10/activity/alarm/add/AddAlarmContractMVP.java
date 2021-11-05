package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

public interface AddAlarmContractMVP {
    interface View {
        void showAlarm(Alarm alarm);
        void updateAlarmDate();
        void goBackToPreviousActivity();
    }

    interface Presenter {
        void downloadAlarm(Bundle bundle);
        void saveAlarm(Alarm alarm);
    }
}
