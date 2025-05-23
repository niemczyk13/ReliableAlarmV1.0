package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.AlarmListType;
import com.example.globals.enums.TypeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.fragment.alarm.list.data.Model;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmListPresenter extends BasePresenter<AlarmListContractMVP.View> implements AlarmListContractMVP.Presenter {
    private TypeView typeView;
    private final Model model;
    private final AlarmListType alarmListType;
    private long groupAlarmId;
    private final Context context;

    public AlarmListPresenter(Context context, AlarmListType alarmListType) {
        super();
        model = new Model(context);
        typeView = TypeView.NORMAL;
        this.alarmListType = alarmListType;
        this.context = context;
    }

    @Override
    public void initView() {
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            initViewForGroupAlarm(view.getGroupAlarmId());
        } else {
            initViewForAllAlarms();
        }
    }

    private void initViewForGroupAlarm(long groupAlarmId) {
        this.groupAlarmId = groupAlarmId;
        GroupAlarmModel groupAlarmModel = model.getGroupAlarm(groupAlarmId);
        view.showFragment(groupAlarmModel.getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
        view.setAppTitleInActionBar(createTitleForGroupAlarm(groupAlarmModel));
        view.showEditButtonInActionBar();
    }

    private @NonNull String createTitleForGroupAlarm(GroupAlarmModel groupAlarmModel) {
        return context.getString(R.string.group_alarm) + ": " + groupAlarmModel.getName();
    }

    private void initViewForAllAlarms() {
        List<Alarm> allAlarms = getAlarms();
        view.showFragment(allAlarms);
        view.setAppTitleInActionBar(context.getString(R.string.title));
    }

    private @NonNull List<Alarm> getAlarms() {
        List<GroupAlarmModel> groupAlarms = model.getGroupAlarms();
        List<SingleAlarmModel> singleAlarms = model.getAllSingleAlarmsWithoutGroupId();
        List<Alarm> allAlarms = new ArrayList<>();
        allAlarms.addAll(groupAlarms);
        allAlarms.addAll(singleAlarms);
        return allAlarms;
    }

    @Override
    public void onBinButtonClick() {
        if (typeView == TypeView.DELETE) {
            resetViewToNormalState();
        } else if (typeView == TypeView.NORMAL) {
            view.showAlarmListForDeletion();
            typeView = TypeView.DELETE;
        }
    }

    @Override
    public void onEditButtonClick() {
        //TODO
        view.showUpdateGroupAlarmDialog(model.getGroupAlarm(groupAlarmId));
    }

    @Override
    public void onDeleteButtonClick(List<Alarm> alarms) {
        stopDeletedAlarms(alarms);
        model.deleteAlarms(alarms);
        resetViewToNormalState();
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM)
            view.updateAlarmList(model.getGroupAlarm(groupAlarmId).getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
        else
            view.updateAlarmList(getAlarms());
        view.updateNotification(model.getActiveSingleAlarms());
    }

    private void stopDeletedAlarms(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            if (alarm instanceof SingleAlarmModel && alarm.isActive()) {
                AlarmManagerManagement.stopAlarm((SingleAlarmModel) alarm, context);
            } else if (alarm instanceof GroupAlarmModel groupAlarmModel) {
                stopAllAlarmsInDeletedGroupAlarm(groupAlarmModel);
            }
        }
    }

    private void stopAllAlarmsInDeletedGroupAlarm(GroupAlarmModel groupAlarmModel) {
        List<SingleAlarmModel> singleAlarmModels = groupAlarmModel.getAlarms();
        singleAlarmModels.forEach(singleAlarmModel -> {
            if (groupAlarmModel.isActive() && singleAlarmModel.isActive()) {
                AlarmManagerManagement.stopAlarm(singleAlarmModel, context);
            }
        });
    }

    @Override
    public void onCancelButtonClick() {
        resetViewToNormalState();
    }

    private void resetViewToNormalState() {
        view.showNormalView();
        typeView = TypeView.NORMAL;
    }

    @Override
    public void onCreateAlarmButtonClick() {
        view.hideAddSingleAndGroupAlarmButtons();
        view.hideFullScreenMask();
        view.showCreateNewAlarmActivity(AddSingleAlarmType.INDIVIDUAL_ALARM);
    }

    @Override
    public void onSwitchOnOffAlarmClick(Alarm alarm) {
        if (alarm instanceof SingleAlarmModel singleAlarm) {
            switchOnOffSingleAlarmClick(singleAlarm);
        } else if (alarm instanceof GroupAlarmModel groupAlarm) {
            switchOnOffGroupAlarmClick(groupAlarm);
        }
    }

    private void switchOnOffGroupAlarmClick(GroupAlarmModel groupAlarm) {
        changeGroupAlarmActive(groupAlarm);

        for (SingleAlarmModel singleAlarm : groupAlarm.getAlarms()) {
            if (groupAlarm.isActive() && singleAlarm.isActive()) {
                AlarmManagerManagement.startAlarm(singleAlarm, context);
            } else if (!groupAlarm.isActive() && singleAlarm.isActive()) {
                updateAlarmTimeAfterNap(singleAlarm);
                AlarmManagerManagement.stopAlarm(singleAlarm, context);
            }
        }

        updateAlarmListAndNotifications();
    }

    private void switchOnOffSingleAlarmClick(SingleAlarmModel singleAlarm) {
        if (singleAlarm.isInGroupAlarm()) {
            switchOnOffSingleAlarmLocatedInGroupAlarm(singleAlarm);
        } else {
            switchOnOffFreeSingleAlarm(singleAlarm);
        }
    }

    private void switchOnOffFreeSingleAlarm(SingleAlarmModel singleAlarm) {
        changeSingleAlarmActive(singleAlarm);

        if (singleAlarm.isActive()) {
            AlarmManagerManagement.startAlarm(singleAlarm, context);
        } else {
            updateAlarmTimeAfterNap(singleAlarm);
            AlarmManagerManagement.stopAlarm(singleAlarm, context);
        }

        updateAlarmListAndNotifications();
    }

    private void switchOnOffSingleAlarmLocatedInGroupAlarm(SingleAlarmModel singleAlarm) {
        changeSingleAlarmActive(singleAlarm);

        GroupAlarmModel groupAlarmModel = model.getGroupAlarm(singleAlarm.getGroupAlarmId());
        if (groupAlarmModel.isActive() && singleAlarm.isActive()) {
            AlarmManagerManagement.startAlarm(singleAlarm, context);
        } else if (groupAlarmModel.isActive()) {
            updateAlarmTimeAfterNap(singleAlarm);
            AlarmManagerManagement.stopAlarm(singleAlarm, context);
        }

        updateAlarmListAndNotifications();
    }

    private void changeGroupAlarmActive(GroupAlarmModel groupAlarm) {
        groupAlarm.setActive(!groupAlarm.isActive());
        model.updateAlarm(groupAlarm);
    }

    private void changeSingleAlarmActive(SingleAlarmModel singleAlarm) {
        singleAlarm.setAlarmDateTime(AlarmDateTimeUpdater.update(singleAlarm.getAlarmDateTime()));
        singleAlarm.setActive(!singleAlarm.isActive());
        model.updateAlarm(singleAlarm);
    }

    public void updateAlarmListAndNotifications() {
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM)
            view.updateAlarmList(model.getGroupAlarm(groupAlarmId).getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
        else
            view.updateAlarmList(getAlarms());

        view.updateNotification(model.getActiveSingleAlarms());
    }

    private void updateAlarmTimeAfterNap(SingleAlarmModel singleAlarm) {
        if (singleAlarm.getNap().isActive()) {
            singleAlarm.getAlarmDateTime().getDateTime().add(Calendar.MINUTE, -singleAlarm.getNap().getTheSumOfTheNapTimes());
        }
    }

    @Override
    public void onAlarmListItemClick(Alarm alarm, int positionOnList) {
        if (typeView == TypeView.NORMAL) {
            if (alarm instanceof SingleAlarmModel singleAlarmModel)
                view.showUpdateAlarmActivity(singleAlarmModel);
            else if (alarm instanceof GroupAlarmModel groupAlarmModel)
                view.showGroupAlarmActivity(groupAlarmModel);
        } else if (typeView == TypeView.DELETE) {
            view.checkOrUncheckAlarm(positionOnList);
        }
    }

    @Override
    public void onCreateGroupAlarmButtonClick() {
        view.hideAddSingleAndGroupAlarmButtons();
        view.showCreateNewAlarmDialog();
        view.setAppTitleInActionBar(context.getString(R.string.add_group_alarm));
    }

    @Override
    public void onAddNewAlarmButtonClick() {
        if (alarmListType == AlarmListType.WITH_GROUP_ALARM) {
            if (view.areAddSingleAndGroupAlarmButtonsVisible()) {
                resetViewState();
            } else {
                view.showAddSingleAndGroupAlarmButtons();
                view.showFullScreenMask();
                view.setAppTitleInActionBar(context.getString(R.string.select_alarm_type));
            }
        } else if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            view.showCreateNewAlarmActivityForGroupAlarm(groupAlarmId, AddSingleAlarmType.FOR_GROUP_ALARM);
        }
    }

    @Override
    public void onFullScreenMaskViewClick() {
        if (view.areAddSingleAndGroupAlarmButtonsVisible() && !view.isAddGroupAlarmDialogShow()) {
            resetViewState();
        }
    }

    @Override
    public void refreshTitleInActionBar() {
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM) {
            GroupAlarmModel groupAlarmModel = model.getGroupAlarm(groupAlarmId);
            view.setAppTitleInActionBar(createTitleForGroupAlarm(groupAlarmModel));
        } else {
            view.setAppTitleInActionBar(context.getString(R.string.title));
        }
    }

    private void resetViewState() {
        view.hideAddSingleAndGroupAlarmButtons();
        view.hideFullScreenMask();
        view.setAppTitleInActionBar(context.getString(R.string.title));
    }

}
