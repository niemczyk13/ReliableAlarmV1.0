package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private final AlarmDataBase alarmDataBase;

    public Model(Context context) {
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    public List<SingleAlarm> getAllAlarms() {
        return alarmDataBase.getAllAlarms();
    }

    public SingleAlarm getAlarm(long id) {
        return alarmDataBase.getAlarm(id);
    }

    public void updateAlarm(SingleAlarm singleAlarm) {
        alarmDataBase.updateAlarm(singleAlarm);
    }

    public void deleteAlarms(List<SingleAlarm> singleAlarms) {
        for (SingleAlarm singleAlarm : singleAlarms) {
            alarmDataBase.deleteAlarm(singleAlarm);
        }
    }

    public List<SingleAlarm> getActiveAlarms() {
        return alarmDataBase.getActiveAlarms();
    }
}
