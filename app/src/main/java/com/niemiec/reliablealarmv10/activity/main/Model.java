package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private AlarmDataBase alarmDataBase;

    public Model(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    List<Alarm> getAllAlarms() {
        return alarmDataBase.getAllAlarms();
    }

    public Alarm getAlarm(long id) {
        return alarmDataBase.getAlarm(id);
    }

    public void updateAlarm(Alarm alarm) {
        alarmDataBase.updateAlarm(alarm);
    }

    /*public List<Alarm> getAllAlarmsSortedByTimeIncrement() {
        List<Alarm> alarms = alarmDataBase.getAllAlarms();
        return alarms.stream().sorted(Alarm::compareTimeTo).collect(Collectors.toList());
    }*/
}
