package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

public interface AddAlarmContractMVP {
    interface View {
        void showAlarm(SingleAlarmEntity singleAlarm);
        SingleAlarmEntity getAlarm();
        void goBackToPreviousActivity();
        void startAlarm(SingleAlarmEntity singleAlarm);
        void stopAlarm(SingleAlarmEntity singleAlarm);
        void updateNotification(List<SingleAlarmEntity> activeSingleAlarms);

    }

    interface Presenter {
        void downloadAlarm(Bundle bundle);
        void saveAlarm();
    }
}
