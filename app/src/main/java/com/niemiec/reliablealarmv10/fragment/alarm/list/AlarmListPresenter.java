package com.niemiec.reliablealarmv10.fragment.alarm.list;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.main.Model;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.utilities.enums.AlarmListType;

import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmListPresenter extends BasePresenter<AlarmListContractMVP.View> implements AlarmListContractMVP.Presenter {
    private TypeView typeView;
    private final Model model;
    private final Context context;

    public AlarmListPresenter(Context context, AlarmListType alarmListType) {
        super();
        model = new Model(context);
        typeView = TypeView.NORMAL;
        this.context = context;
    }

    @Override
    public void initView() {
        view.showFragment(model.getAllAlarms());
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
    public void onDeleteButtonClick(List<SingleAlarmEntity> singleAlarms) {
        stopDeletedAlarms(singleAlarms);
        model.deleteAlarms(singleAlarms);
        resetViewToNormalState();
        view.updateAlarmList(model.getAllAlarms());
        view.updateNotification(model.getActiveAlarms());
    }

    private void stopDeletedAlarms(List<SingleAlarmEntity> singleAlarms) {
        for (SingleAlarmEntity singleAlarm : singleAlarms) {
            if (singleAlarm.isActive) {
                view.stopAlarm(singleAlarm);
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
        SingleAlarmEntity singleAlarm = model.getAlarm(id);
        singleAlarm.alarmDateTime = AlarmDateTimeUpdater.update(singleAlarm.alarmDateTime);
        singleAlarm.isActive = !singleAlarm.isActive;
        model.updateAlarm(singleAlarm);

        if (singleAlarm.isActive) {
            view.startAlarm(singleAlarm);
        } else {
            updateAlarmTimeAfterNap(singleAlarm);
            view.stopAlarm(singleAlarm);
        }
        view.updateAlarmList(model.getAllAlarms());
        view.updateNotification(model.getActiveAlarms());
    }

    private void updateAlarmTimeAfterNap(SingleAlarmEntity singleAlarm) {
        if (singleAlarm.nap.isActive()) {
            singleAlarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, -singleAlarm.nap.getTheSumOfTheNapTimes());
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
