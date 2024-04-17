package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarmSettings;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.Calendar;
import java.util.List;

import androidx.room.Room;

public class AlarmDataBase {
    private static AlarmDataBase instance;
    private static AlarmDataBaseModel dataBaseModel;

    private AlarmDataBase(Context context) {
        createDataBase(context);
    }

    private static void createDataBase(Context context) {
        dataBaseModel = Room.databaseBuilder(context, AlarmDataBaseModel.class, "alarmDataBase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        BasicAlarmSettings basicAlarmSettings = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        if (basicAlarmSettings == null)
            dataBaseModel.basicAlarmDAO().insertBasicAlarm(new BasicAlarmSettings());
    }

    public void insertAlarm(SingleAlarm singleAlarm) {
        dataBaseModel.alarmDAO().insertAlarm(singleAlarm);
    }

    public SingleAlarm getDefaultAlarm() {
        BasicAlarmSettings basicAlarmSettings = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return basicAlarmSettings.getAlarm();
    }

    public SingleAlarm getAlarm(long id) {
        return dataBaseModel.alarmDAO().getAlarm(id);
    }

    public void updateAlarm(SingleAlarm singleAlarm) {
        dataBaseModel.alarmDAO().updateAlarm(singleAlarm);
    }

    public List<SingleAlarm> getAllAlarms() {
        return dataBaseModel.alarmDAO().getAll();
    }

    public List<SingleAlarm> getAlarmsBefore(Calendar date) {
        return dataBaseModel.alarmDAO().getAlarmsBefore(date.getTimeInMillis());
    }

    public static AlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmDataBase(context);
        }
        return instance;
    }

    public void deleteAlarm(SingleAlarm singleAlarm) {
        dataBaseModel.alarmDAO().deleteAlarm(singleAlarm);
    }

    public SingleAlarm getLastAlarm() {return dataBaseModel.alarmDAO().getLastAlarm();}

    public List<SingleAlarm> getActiveAlarms() {return dataBaseModel.alarmDAO().getActiveAlarms();}
}
