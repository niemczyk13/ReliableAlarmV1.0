package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public class Model {
    private AlarmDataBase alarmDataBase;

    public Model(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    List<Alarm> getAllAlarms() {
        return alarmDataBase.getAllAlarms();
    }
}
