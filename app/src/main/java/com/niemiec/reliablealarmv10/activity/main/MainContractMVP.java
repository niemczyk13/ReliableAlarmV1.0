package com.niemiec.reliablealarmv10.activity.main;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showActivity(List<SingleAlarm> singleAlarms);
        void showAlarmListForDeletion();
        void showNormalView();
        void updateAlarmList(List<SingleAlarm> singleAlarms);
        void showCreateNewAlarmActivity();
        void showUpdateAlarmActivity(int position);
        void checkOrUncheckAlarm(int position);
        void startAlarm(SingleAlarm singleAlarm);
        void stopAlarm(SingleAlarm singleAlarm);
        void updateNotification(List<SingleAlarm> activeSingleAlarms);
    }

    interface Presenter {
        void initView();
        void onBinButtonClick();
        void onDeleteButtonClick(List<SingleAlarm> singleAlarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(long id);
        void onAlarmListItemClick(int position);
    }
}
