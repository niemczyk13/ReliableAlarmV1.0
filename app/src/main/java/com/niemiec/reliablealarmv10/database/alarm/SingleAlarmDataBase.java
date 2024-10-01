package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.Calendar;
import java.util.List;

public class SingleAlarmDataBase {
    private static SingleAlarmDataBase instance;
    private static AlarmDataBaseModel dataBaseModel;

    private SingleAlarmDataBase(Context context) {
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

    public void insertSingleAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.singleAlarmDAO().insertAlarm(singleAlarm);
    }

    public SingleAlarmEntity getDefaultSingleAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return basicAlarm.getAlarm();
    }

    public SingleAlarmEntity getSingleAlarm(long id) {
        return dataBaseModel.singleAlarmDAO().getAlarm(id);
    }

    public void updateSingleAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.singleAlarmDAO().updateAlarm(singleAlarm);
    }

    public List<SingleAlarmEntity> getAllSingleAlarms() {
        return dataBaseModel.singleAlarmDAO().getAll();
    }

    public List<SingleAlarmEntity> getSingleAlarmsBefore(Calendar date) {
        return dataBaseModel.singleAlarmDAO().getAlarmsBefore(date.getTimeInMillis());
    }

    public static SingleAlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new SingleAlarmDataBase(context);
        }
        return instance;
    }

    public void deleteSingleAlarm(SingleAlarmEntity singleAlarm) {
        dataBaseModel.singleAlarmDAO().deleteAlarm(singleAlarm);
    }

    public SingleAlarmEntity getLastSingleAlarm() {return dataBaseModel.singleAlarmDAO().getLastAlarm();}

    public List<SingleAlarmEntity> getActiveSingleAlarms() {return dataBaseModel.singleAlarmDAO().getActiveAlarms();}
}
