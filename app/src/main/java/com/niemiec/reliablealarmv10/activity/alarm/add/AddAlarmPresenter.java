package com.niemiec.reliablealarmv10.activity.alarm.add;

import static com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager.updateNotification;

import android.content.Context;
import android.os.Bundle;

import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.List;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {
    private final SingleAlarmDataBase singleAlarmDataBase;
    private final GroupAlarmDataBase groupAlarmDataBase;
    private Type type;
    private long id;

    public AddAlarmPresenter(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
        groupAlarmDataBase = GroupAlarmDataBase.getInstance(context);
    }

    @Override
    public void downloadAlarm(Bundle bundle) {
        type = (Type) bundle.getSerializable(BundleNames.TYPE.name());
        if (type == Type.CREATE) {
            id = 0;
            view.showAlarm(singleAlarmDataBase.getDefaultSingleAlarm());
        } else if (type == Type.UPDATE) {
            id = bundle.getLong(BundleNames.ALARM_ID.name());
            view.showAlarm(singleAlarmDataBase.getSingleAlarm(id));
        }
    }

    @Override
    public void saveAlarm() {
        if (type == Type.CREATE)
            saveNewAlarm();
        else if (type == Type.UPDATE)
            updateAlarm();
        updateNotification();
    }

    private void updateNotification() {
        view.updateNotification(singleAlarmDataBase.getActiveSingleAlarmsWithGroupAlarmActiveIncluded());
    }

    private void saveNewAlarm() {
        SingleAlarmModel singleAlarm = view.getAlarm();
        singleAlarmDataBase.insertSingleAlarm(singleAlarm);
        singleAlarm.setId(singleAlarmDataBase.getLastSingleAlarm().getId());

        if (singleAlarm.isInGroupAlarm()) {
            GroupAlarmModel groupAlarmModel = groupAlarmDataBase.getGroupAlarm(singleAlarm.getGroupAlarmId());
            if (groupAlarmModel.isActive()) {
                view.startAlarm(singleAlarm);
            }
        } else {
            view.startAlarm(singleAlarm);
        }
        view.goBackToPreviousActivity();
    }

    private void updateAlarm() {
        SingleAlarmModel singleAlarm = singleAlarmDataBase.getSingleAlarm(id);
        view.stopAlarm(singleAlarm);
        SingleAlarmModel updateSingleAlarm = view.getAlarm();
        updateSingleAlarm.setId(id);
        singleAlarmDataBase.updateSingleAlarm(updateSingleAlarm);

        if (updateSingleAlarm.isInGroupAlarm()) {
            GroupAlarmModel groupAlarmModel = groupAlarmDataBase.getGroupAlarm(updateSingleAlarm.getGroupAlarmId());
            if (groupAlarmModel.isActive()) {
                view.startAlarm(updateSingleAlarm);
            }
        } else {
            view.startAlarm(updateSingleAlarm);
        }

        view.goBackToPreviousActivity();
    }

    public enum Type {
        CREATE, UPDATE
    }
}
