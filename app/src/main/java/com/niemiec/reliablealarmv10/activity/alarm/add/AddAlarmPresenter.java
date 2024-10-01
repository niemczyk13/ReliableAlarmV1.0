package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.content.Context;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {
    private final SingleAlarmDataBase singleAlarmDataBase;
    private Type type;
    private long id;

    public AddAlarmPresenter(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
    }

    @Override
    public void downloadAlarm(Bundle bundle) {
        type = (Type) bundle.getSerializable("type");
        if (type == Type.CREATE) {
            id = 0;
            view.showAlarm(singleAlarmDataBase.getDefaultSingleAlarm());
        } else if (type == Type.UPDATE) {
            id = bundle.getLong("alarm_id");
            view.showAlarm(singleAlarmDataBase.getSingleAlarm(id));
        }
    }

    @Override
    public void saveAlarm() {
        if (type == Type.CREATE)
            saveNewAlarm();
        else if (type == Type.UPDATE)
            updateAlarm();
        view.updateNotification(singleAlarmDataBase.getActiveSingleAlarms());
    }

    private void saveNewAlarm() {
        SingleAlarmEntity singleAlarm = view.getAlarm();
        singleAlarmDataBase.insertSingleAlarm(singleAlarm);
        singleAlarm.id = singleAlarmDataBase.getLastSingleAlarm().id;
        view.startAlarm(singleAlarm);
        view.goBackToPreviousActivity();
    }

    private void updateAlarm() {
        SingleAlarmEntity singleAlarm = singleAlarmDataBase.getSingleAlarm(id);
        view.stopAlarm(singleAlarm);
        SingleAlarmEntity updateSingleAlarm = view.getAlarm();
        updateSingleAlarm.id = id;
        singleAlarmDataBase.updateSingleAlarm(updateSingleAlarm);
        view.startAlarm(updateSingleAlarm);
        view.goBackToPreviousActivity();
    }

    public enum Type {
        CREATE, UPDATE
    }
}
