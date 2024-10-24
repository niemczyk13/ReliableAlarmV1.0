package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.example.globals.enums.AlarmListType;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.fragment.alarm.list.data.Model;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
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
    private AlarmListType alarmListType;
    private long groupAlarmId;

    public AlarmListPresenter(Context context, AlarmListType alarmListType) {
        super();
        model = new Model(context);
        typeView = TypeView.NORMAL;
        this.alarmListType = alarmListType;
    }

    @Override
    public void initViewForGroupAlarm(long groupAlarmId) {
        this.groupAlarmId = groupAlarmId;
        GroupAlarmModel groupAlarmModel = model.getGroupAlarm(groupAlarmId);
        view.showFragment(groupAlarmModel.getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
    }

    @Override
    public void initViewForAllAlarms() {
        List<Alarm> allAlarms = getAlarms();
        view.showFragment(allAlarms);
    }

    private @NonNull List<Alarm> getAlarms() {
        List<GroupAlarmModel> groupAlarms = model.getGroupAlarms();
        List<SingleAlarmModel> singleAlarms = model.getAllSingleAlarms();
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
    public void onDeleteButtonClick(List<Alarm> alarms) {
        stopDeletedAlarms(alarms);
        model.deleteAlarms(alarms);
        resetViewToNormalState();
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM)
            view.updateAlarmList(model.getGroupAlarm(groupAlarmId).getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
        else
            view.updateAlarmList(getAlarms());
        view.updateNotification(model.getActiveAlarms());
    }

    private void stopDeletedAlarms(List<Alarm> alarms) {
        //TODO tutaj powinno działać tak, że zamknie wszystkie alarmy z GroupAlarmModel
        for (Alarm alarm : alarms) {
            if (alarm instanceof SingleAlarmModel && alarm.isActive()) {
                view.stopAlarm((SingleAlarmModel) alarm);
            } else if (alarm instanceof GroupAlarmModel) {
                GroupAlarmModel groupAlarmModel = (GroupAlarmModel) alarm;
                List<SingleAlarmModel> singleAlarmModels = groupAlarmModel.getAlarms();
                singleAlarmModels.forEach(singleAlarmModel -> {
                    if (singleAlarmModel.isActive()) {
                        view.stopAlarm(singleAlarmModel);
                    }
                });
            }
        }
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
        view.showCreateNewAlarmActivity();
    }

    @Override
    public void onSwitchOnOffAlarmClick(long id) {
        //TODO
        Alarm alarm = model.getAlarm(id);
        if (alarm instanceof  SingleAlarmModel) {

        } else if (alarm instanceof GroupAlarmModel) {

        }

        SingleAlarmModel singleAlarm = model.getAlarm(id);
        singleAlarm.setAlarmDateTime(AlarmDateTimeUpdater.update(singleAlarm.getAlarmDateTime()));
        singleAlarm.setActive(!singleAlarm.isActive());
        model.updateAlarm(singleAlarm);

        if (singleAlarm.isActive()) {
            view.startAlarm(singleAlarm);
        } else {
            updateAlarmTimeAfterNap(singleAlarm);
            view.stopAlarm(singleAlarm);
        }
        if (alarmListType == AlarmListType.WITHOUT_GROUP_ALARM)
            view.updateAlarmList(model.getGroupAlarm(groupAlarmId).getAlarms().stream().map(a -> (Alarm) a).collect(Collectors.toList()));
        else
            view.updateAlarmList(getAlarms());
        view.updateNotification(model.getActiveAlarms());
    }

    private void updateAlarmTimeAfterNap(SingleAlarmModel singleAlarm) {
        if (singleAlarm.getNap().isActive()) {
            singleAlarm.getAlarmDateTime().getDateTime().add(Calendar.MINUTE, -singleAlarm.getNap().getTheSumOfTheNapTimes());
        }
    }

    @Override
    public void onAlarmListItemClick(int position) {
        if (typeView == TypeView.NORMAL) {
            view.showUpdateAlarmActivity(position);
        } else if (typeView == TypeView.DELETE) {
            view.checkOrUncheckAlarm(position);
        }
    }

    @Override
    public void onCreateGroupAlarmButtonClick() {
        view.hideAddSingleAndGroupAlarmButtons();
        view.showCreateNewAlarmDialog();
    }

    @Override
    public void onAddNewAlarmButtonClick() {
        if (view.areAddSingleAndGroupAlarmButtonsVisible()) {
            view.hideAddSingleAndGroupAlarmButtons();
            view.hideFullScreenMask();
        } else {
            view.showAddSingleAndGroupAlarmButtons();
            view.showFullScreenMask();
        }
    }

    @Override
    public void onFullScreenMaskViewClick() {
        if (view.areAddSingleAndGroupAlarmButtonsVisible() && !view.isAddGroupAlarmDialogShow()) {
            view.hideAddSingleAndGroupAlarmButtons();
            view.hideFullScreenMask();
        }
    }

    enum TypeView {
        NORMAL, DELETE
    }
}
