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
        if (type == Type.CREATE)
            saveNewAlarm();
        else if (type == Type.UPDATE)
            updateAlarm();

//        Alarm alarm = view.getAlarm();
//        view.stopAlarm(alarm);
//        addAlarmToDatabase(alarm);
//        alarm = alarmDataBase.getLastAlarm();
//        view.goBackToPreviousActivity();
//        //TODO aktywowanie alarmu - to samo co w MainPresenter
//        if (alarm.isActive)
//            view.startAlarm(alarm);
//        else
//            view.stopAlarm(alarm);
    }

    private void saveNewAlarm() {
        Alarm alarm = view.getAlarm();
        alarmDataBase.insertAlarm(alarm);
        alarm.id = alarmDataBase.getLastAlarm().id;
        view.startAlarm(alarm);
        view.goBackToPreviousActivity();
    }

    private void updateAlarm() {
        Alarm alarm = alarmDataBase.getAlarm(id);
        view.stopAlarm(alarm);
        Alarm updateAlarm = view.getAlarm();
        updateAlarm.id = id;
        alarmDataBase.updateAlarm(updateAlarm);
        view.startAlarm(updateAlarm);
        view.goBackToPreviousActivity();
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
