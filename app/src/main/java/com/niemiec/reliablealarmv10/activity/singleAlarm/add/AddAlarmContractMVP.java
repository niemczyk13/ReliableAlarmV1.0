package com.niemiec.reliablealarmv10.activity.singleAlarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;

public interface AddAlarmContractMVP {
    interface View {
        void showAlarm(SingleAlarm singleAlarm);
        SingleAlarm getAlarm();
        void goBackToPreviousActivity();
        void startAlarm(SingleAlarm singleAlarm);
        void stopAlarm(SingleAlarm singleAlarm);
        void updateNotification(List<SingleAlarm> activeSingleAlarms);

    }

    interface Presenter {
        void downloadAlarm(Bundle bundle);
        void saveAlarm();
    }
}
