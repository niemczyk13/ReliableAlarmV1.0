package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarm;

import androidx.room.Room;

public class AlarmDataBase {
    private static AlarmDataBaseModel dataBaseModel;

    public static void createDataBase(Context context) {
        dataBaseModel = Room.databaseBuilder(context, AlarmDataBaseModel.class, "alarmDataBase").allowMainThreadQueries().build();
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        dataBaseModel.basicAlarmDAO().insertBasiAlarm(new BasicAlarm());
    }


}
