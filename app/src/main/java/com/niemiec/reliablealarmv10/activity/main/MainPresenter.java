package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainPresenter extends BasePresenter<MainContractMVP.View> implements MainContractMVP.Presenter {
    private TypeView typeView;
    private final Model model;

    public MainPresenter(Context context) {
        super();
        model = new Model(context);
        typeView = TypeView.NORMAL;
    }

    @Override
    public void initView() {
        view.showActivity(model.getAllAlarms());
    }

    @Override
    public void onBinButtonClick() {
        if (typeView == TypeView.DELETE) {
            view.showNormalView();
            typeView = TypeView.NORMAL;
        } else if (typeView == TypeView.NORMAL) {
            view.showAlarmListForDeletion();
            typeView = TypeView.DELETE;
        }
    }

    @Override
    public void onDeleteButtonClick(List<SingleAlarm> singleAlarms) {
        stopDeletedAlarms(singleAlarms);
        model.deleteAlarms(singleAlarms);
        view.showNormalView();
        view.updateAlarmList(model.getAllAlarms());
        typeView = TypeView.NORMAL;
        view.updateNotification(model.getActiveAlarms());
    }

    private void stopDeletedAlarms(List<SingleAlarm> singleAlarms) {
        for (SingleAlarm singleAlarm : singleAlarms) {
            if (singleAlarm.isActive) {
                view.stopAlarm(singleAlarm);
            }
        }
    }

    @Override
    public void onCancelButtonClick() {
        view.showNormalView();
        typeView = TypeView.NORMAL;
    }

    @Override
    public void onCreateAlarmButtonClick() {
        view.showCreateNewAlarmActivity();
    }

    @Override
    public void onSwitchOnOffAlarmClick(long id) {
        SingleAlarm singleAlarm = model.getAlarm(id);
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

    private void updateAlarmTimeAfterNap(SingleAlarm singleAlarm) {
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

    enum TypeView {
        NORMAL, DELETE
    }
}
