package com.niemiec.reliablealarmv10.fragment.alarm.list;

import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

public interface AlarmListContractMVP {
    interface View {
        void showFragment(List<SingleAlarmModel> singleAlarms);
        void showFragment(List<GroupAlarmModel> groupAlarms, List<SingleAlarmModel> singleAlarms);
        void showAlarmListForDeletion();
        void showNormalView();
        void updateAlarmList(List<SingleAlarmModel> singleAlarms);
        void updateAlarmListForSingleAlarmModel(List<SingleAlarmModel> singleAlarms);
        void showCreateNewAlarmActivity();
        void showUpdateAlarmActivity(int position);
        void showGroupAlarmActivity(long groupAlarmId);
        void checkOrUncheckAlarm(int position);
        void startAlarm(SingleAlarmModel singleAlarm);
        void stopAlarm(SingleAlarmModel singleAlarm);
        void updateNotification(List<SingleAlarmModel> activeSingleAlarms);
        void showCreateNewAlarmDialog();
        void showAddSingleAndGroupAlarmButtons();
        boolean areAddSingleAndGroupAlarmButtonsVisible();
        void hideAddSingleAndGroupAlarmButtons();
        void showFullScreenMask();
        void hideFullScreenMask();
        boolean isAddGroupAlarmDialogShow();
    }

    interface Presenter {
        void initViewForGroupAlarm(long groupAlarmId);
        void initViewForAllAlarms();
        void onBinButtonClick();
        void onDeleteButtonClick(List<SingleAlarmModel> singleAlarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(long id);
        void onAlarmListItemClick(int position);
        void onCreateGroupAlarmButtonClick();
        void onAddNewAlarmButtonClick();
        void onFullScreenMaskViewClick();
    }
}
