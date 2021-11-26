package com.niemiec.reliablealarmv10.activity.main;

import android.content.Intent;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showMainAlarmList(List<Alarm> alarms);
        void showAlarmListForDeletion();
        void updateAlarmList(List<Alarm> alarms);
        void showNewActivity(Intent intent);
    }

    interface Presenter {
        void initView();
        void onBinButtonClick();
        void onDeleteButtonClick(List<Alarm> alarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick();
        void onUpdateAlarmClick();
    }
}
