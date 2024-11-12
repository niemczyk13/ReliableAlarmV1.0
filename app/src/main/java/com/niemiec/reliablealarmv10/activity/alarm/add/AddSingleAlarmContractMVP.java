package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

public interface AddSingleAlarmContractMVP {
    interface View {
        void showAlarm(SingleAlarmModel singleAlarm);
        SingleAlarmModel getAlarm();
        void goBackToPreviousActivity();
        void startAlarm(SingleAlarmModel singleAlarm);
        void stopAlarm(SingleAlarmModel singleAlarm);
        void updateNotification(List<SingleAlarmModel> activeSingleAlarms);

    }

    interface Presenter {
        void downloadAlarm(Bundle bundle);
        void saveAlarm();
    }
}
