package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;

public class Model {
    private AlarmDataBase alarmDataBase;

    public Model(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }
}
