package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.Calendar;
import java.util.List;

public class AlarmDataBase {
    private static AlarmDataBase instance;
    private static AlarmDataBaseModel dataBaseModel;

    private AlarmDataBase(Context context) {
        createDataBase(context);
    }

    private static void createDataBase(Context context) {
        dataBaseModel = AlarmDataBaseModel.getInstance(context);
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        if (basicAlarm == null)
            dataBaseModel.basicAlarmDAO().insertBasicAlarm(new BasicAlarm());
    }

    public void insertAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.alarmDAO().insertAlarm(singleAlarm);
    }

    public SingleAlarmEntity getDefaultAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return basicAlarm.getAlarm();
    }

    public SingleAlarmEntity getAlarm(long id) {
        return dataBaseModel.alarmDAO().getAlarm(id);
    }

    public void updateAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.alarmDAO().updateAlarm(singleAlarm);
    }

    public List<SingleAlarmEntity> getAllAlarms() {
        return dataBaseModel.alarmDAO().getAll();
    }

    public List<SingleAlarmEntity> getAlarmsBefore(Calendar date) {
        return dataBaseModel.alarmDAO().getAlarmsBefore(date.getTimeInMillis());
    }

    public static AlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmDataBase(context);
        }
        return instance;
    }

    public void deleteAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.alarmDAO().deleteAlarm(singleAlarm);
    }

    public SingleAlarmEntity getLastAlarm() {return dataBaseModel.alarmDAO().getLastAlarm();}

    public List<SingleAlarmEntity> getActiveAlarms() {return dataBaseModel.alarmDAO().getActiveAlarms();}
}
