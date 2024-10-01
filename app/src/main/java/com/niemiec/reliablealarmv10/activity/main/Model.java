package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private final SingleAlarmDataBase singleAlarmDataBase;

    public Model(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
    }

    public List<SingleAlarmEntity> getAllAlarms() {
        return singleAlarmDataBase.getAllSingleAlarms();
    }

    public SingleAlarmEntity getAlarm(long id) {
        return singleAlarmDataBase.getSingleAlarm(id);
    }

    public void updateAlarm(SingleAlarmEntity singleAlarm) {
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
    }

    public void deleteAlarms(List<SingleAlarmEntity> singleAlarms) {
        for (SingleAlarmEntity singleAlarm : singleAlarms) {
            singleAlarmDataBase.deleteSingleAlarm(singleAlarm);
        }
    }

    public List<SingleAlarmEntity> getActiveAlarms() {
        return singleAlarmDataBase.getActiveSingleAlarms();
    }
}
