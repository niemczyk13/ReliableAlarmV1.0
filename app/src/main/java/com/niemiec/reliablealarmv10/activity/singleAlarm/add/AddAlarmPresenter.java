package com.niemiec.reliablealarmv10.activity.singleAlarm.add;

import android.content.Context;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {
    private final AlarmDataBase alarmDataBase;
    private Type type;
    private long id;

    public AddAlarmPresenter(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    @Override
    public void downloadAlarm(Bundle bundle) {
        type = (Type) bundle.getSerializable("type");
        if (type == Type.CREATE) {
            id = 0;
            view.showAlarm(alarmDataBase.getDefaultAlarm());
        } else if (type == Type.UPDATE) {
            id = bundle.getLong("alarm_id");
            view.showAlarm(alarmDataBase.getAlarm(id));
        }
    }

    @Override
    public void saveAlarm() {
        if (type == Type.CREATE)
            saveNewAlarm();
        else if (type == Type.UPDATE)
            updateAlarm();
        view.updateNotification(alarmDataBase.getActiveAlarms());
    }

    private void saveNewAlarm() {
        SingleAlarm singleAlarm = view.getAlarm();
        alarmDataBase.insertAlarm(singleAlarm);
        singleAlarm.id = alarmDataBase.getLastAlarm().id;
        view.startAlarm(singleAlarm);
        view.goBackToPreviousActivity();
    }

    private void updateAlarm() {
        SingleAlarm singleAlarm = alarmDataBase.getAlarm(id);
        view.stopAlarm(singleAlarm);
        SingleAlarm updateSingleAlarm = view.getAlarm();
        updateSingleAlarm.id = id;
        alarmDataBase.updateAlarm(updateSingleAlarm);
        view.startAlarm(updateSingleAlarm);
        view.goBackToPreviousActivity();
    }

    public enum Type {
        CREATE, UPDATE
    }
}
