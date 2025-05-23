package com.niemiec.reliablealarmv10.fragment.alarm.list;

import com.example.globals.enums.AddSingleAlarmType;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

public interface AlarmListContractMVP {
    interface View {
        long getGroupAlarmId();

        void showFragment(List<Alarm> singleAlarms);
        void showAlarmListForDeletion();
        void showNormalView();
        void updateAlarmList(List<Alarm> alarms);
        void showCreateNewAlarmActivity(AddSingleAlarmType addSingleAlarmType);
        void showCreateNewAlarmActivityForGroupAlarm(long groupAlarmId, AddSingleAlarmType addSingleAlarmType);
        void showUpdateAlarmActivity(SingleAlarmModel singleAlarmModel);
        void showGroupAlarmActivity(GroupAlarmModel groupAlarmModel);
        void checkOrUncheckAlarm(int positionOnList);
        void updateNotification(List<SingleAlarmModel> activeSingleAlarms);
        void showCreateNewAlarmDialog();
        void showUpdateGroupAlarmDialog(GroupAlarmModel groupAlarm);
        void showAddSingleAndGroupAlarmButtons();
        boolean areAddSingleAndGroupAlarmButtonsVisible();
        void hideAddSingleAndGroupAlarmButtons();
        void showFullScreenMask();
        void hideFullScreenMask();
        boolean isAddGroupAlarmDialogShow();
        void setAppTitleInActionBar(String s);
        void showEditButtonInActionBar();
        void refreshTitleInActionBar();
    }

    interface Presenter {
        void initView();
        void onBinButtonClick();
        void onEditButtonClick();
        void onDeleteButtonClick(List<Alarm> alarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(Alarm alarm);
        void onAlarmListItemClick(Alarm alarm, int positionOnList);
        void onCreateGroupAlarmButtonClick();
        void onAddNewAlarmButtonClick();
        void onFullScreenMaskViewClick();
        void refreshTitleInActionBar();
    }
}
