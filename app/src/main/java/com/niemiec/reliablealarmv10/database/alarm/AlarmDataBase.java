package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import androidx.room.Room;

public class AlarmDataBase {
    private static AlarmDataBaseModel dataBaseModel;

    public static void createDataBase(Context context) {
        dataBaseModel = Room.databaseBuilder(context, AlarmDataBaseModel.class, "alarmDataBase").allowMainThreadQueries().build();
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        //System.out.println("BasicAlarm exist: " + basicAlarm.id);
        if (basicAlarm == null)
            dataBaseModel.basicAlarmDAO().insertBasicAlarm(new BasicAlarm());
    }

    public static void insertAlarm(Alarm alarm) {
        dataBaseModel.alarmDAO().insertAlarm(alarm);
    }

    public static Alarm getDefaultAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return basicAlarm.getAlarm();
    }

}
