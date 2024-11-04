package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.content.Context;
import android.os.Bundle;

import com.example.globals.enums.BundleNames;
import com.example.globals.enums.TypeView;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

public class AddSingleAlarmPresenter extends BasePresenter<AddSingleAlarmContractMVP.View> implements AddSingleAlarmContractMVP.Presenter {
    private final SingleAlarmDataBase singleAlarmDataBase;
    private final GroupAlarmDataBase groupAlarmDataBase;
    private TypeView type;
    private long id;

    public AddSingleAlarmPresenter(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
        groupAlarmDataBase = GroupAlarmDataBase.getInstance(context);
        id = 0;
    }

    @Override
    public void downloadAlarm(Bundle bundle) {
        type = (TypeView) bundle.getSerializable(BundleNames.TYPE.name());
        if (type == TypeView.CREATE) {
            view.showAlarm(singleAlarmDataBase.getDefaultSingleAlarm());
        } else if (type == TypeView.UPDATE) {
            id = bundle.getLong(BundleNames.ALARM_ID.name());
            view.showAlarm(singleAlarmDataBase.getSingleAlarm(id));
        }
    }

    @Override
    public void saveAlarm() {
        if (type == TypeView.CREATE)
            saveNewAlarm();
        else if (type == TypeView.UPDATE)
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
        if (singleAlarm.isInGroupAlarm())
            updateSingleAlarm.setGroupAlarmId(singleAlarm.getGroupAlarmId());
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
}
