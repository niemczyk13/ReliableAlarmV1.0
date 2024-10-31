package com.niemiec.reliablealarmv10.fragment.alarm.list;

import com.example.globals.enums.AddSingleAlarmType;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

public interface AlarmListContractMVP {
    interface View {
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
        void onDeleteButtonClick(List<Alarm> alarms);
        void onCancelButtonClick();
        void onCreateAlarmButtonClick();
        void onSwitchOnOffAlarmClick(Alarm alarm);
        void onAlarmListItemClick(Alarm alarm, int positionOnList);
        void onCreateGroupAlarmButtonClick();
        void onAddNewAlarmButtonClick();
        void onFullScreenMaskViewClick();
    }
}
