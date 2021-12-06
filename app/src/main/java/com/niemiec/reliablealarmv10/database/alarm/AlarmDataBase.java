package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

import androidx.room.Room;

public class AlarmDataBase {
    private static AlarmDataBase instance;
    private static AlarmDataBaseModel dataBaseModel;

    private AlarmDataBase(Context context) {
        createDataBase(context);
    }

    private static void createDataBase(Context context) {
        dataBaseModel = Room.databaseBuilder(context, AlarmDataBaseModel.class, "alarmDataBase").allowMainThreadQueries().build();
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        if (basicAlarm == null)
            dataBaseModel.basicAlarmDAO().insertBasicAlarm(new BasicAlarm());
    }

    public void insertAlarm(Alarm alarm) {
        dataBaseModel.alarmDAO().insertAlarm(alarm);
    }

    public Alarm getDefaultAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return basicAlarm.getAlarm();
    }

    public Alarm getAlarm(long id) {
        return dataBaseModel.alarmDAO().getAlarm(id);
    }

    public void updateAlarm(Alarm alarm) {
        dataBaseModel.alarmDAO().updateAlarm(alarm);
    }

    public List<Alarm> getAllAlarms() {
        return dataBaseModel.alarmDAO().getAll();
    }

    public static AlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmDataBase(context);
        }
        return instance;
    }

    public void deleteAlarm(Alarm alarm) {
        dataBaseModel.alarmDAO().deleteAlarm(alarm);
    }
}
