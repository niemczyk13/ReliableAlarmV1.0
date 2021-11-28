package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.content.Context;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {
    private AlarmDataBase alarmDataBase;
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
        Alarm alarm = view.getAlarm();
        addAlarmToDatabase(alarm);
        view.goBackToPreviousActivity();
        //TODO aktywowanie alarmu
    }

    private void addAlarmToDatabase(Alarm alarm) {
        if (type == Type.CREATE) {
            alarmDataBase.insertAlarm(alarm);
        } else if (type == Type.UPDATE) {
            alarm.id = id;
            alarmDataBase.updateAlarm(alarm);
        }
    }

    public enum Type {
        CREATE, UPDATE;
    }
}
