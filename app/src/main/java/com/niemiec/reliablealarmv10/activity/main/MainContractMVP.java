package com.niemiec.reliablealarmv10.activity.main;

import android.content.Intent;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showActivity(List<Alarm> alarms);
        void showAlarmListForDeletion();
        void showNormalView();
        void updateAlarmList(List<Alarm> alarms);
        void showCreateNewAlarmActivity();
        void showUpdateAlarmActivity(int position);
        void checkOrUncheckAlarm(int position);
    }

    interface Presenter {
        void initView();
        void onBinButtonClick();
        void onDeleteButtonClick(List<Alarm> alarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(long id);
        void onAlarmListItemClick(int position);
    }
}
