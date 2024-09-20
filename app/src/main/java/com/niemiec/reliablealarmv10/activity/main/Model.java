package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private final AlarmDataBase alarmDataBase;

    public Model(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    public List<SingleAlarmEntity> getAllAlarms() {
        return alarmDataBase.getAllAlarms();
    }

    public SingleAlarmEntity getAlarm(long id) {
        return alarmDataBase.getAlarm(id);
    }

    public void updateAlarm(SingleAlarmEntity singleAlarm) {
        alarmDataBase.updateAlarm(singleAlarm);
    }

    public void deleteAlarms(List<SingleAlarmEntity> singleAlarms) {
        for (SingleAlarmEntity singleAlarm : singleAlarms) {
            alarmDataBase.deleteAlarm(singleAlarm);
        }
    }

    public List<SingleAlarmEntity> getActiveAlarms() {
        return alarmDataBase.getActiveAlarms();
    }
}
