package com.niemiec.reliablealarmv10.activity.main;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showActivity(List<SingleAlarmEntity> singleAlarms);
        void showAlarmListForDeletion();
        void showNormalView();
        void updateAlarmList(List<SingleAlarmEntity> singleAlarms);
        void showCreateNewAlarmActivity();
        void showUpdateAlarmActivity(int position);
        void checkOrUncheckAlarm(int position);
        void startAlarm(SingleAlarmEntity singleAlarm);
        void stopAlarm(SingleAlarmEntity singleAlarm);
        void updateNotification(List<SingleAlarmEntity> activeSingleAlarms);
        void showCreateNewAlarmDialog();
        void showAddSingleAndGroupAlarmButtons();
        boolean areAddSingleAndGroupAlarmButtonsVisible();
        void hideAddSingleAndGroupAlarmButtons();
        void showFullScreenMask();
        void hideFullScreenMask();
    }

    interface Presenter {
        void initView();
        void onBinButtonClick();
        void onDeleteButtonClick(List<SingleAlarmEntity> singleAlarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(long id);
        void onAlarmListItemClick(int position);
        void onCreateGroupAlarmButtonClick();
        void onAddNewAlarmButtonClick();
        void onFullScreenMaskViewClick();
    }
}
