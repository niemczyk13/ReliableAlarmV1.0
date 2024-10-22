package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private final SingleAlarmDataBase singleAlarmDataBase;

    public Model(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
    }

    public List<SingleAlarmModel> getAllAlarms() {
        return singleAlarmDataBase.getAllSingleAlarms();
    }

    public SingleAlarmModel getAlarm(long id) {
        return singleAlarmDataBase.getSingleAlarm(id);
    }

    public void updateAlarm(SingleAlarmModel singleAlarm) {
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
    }

    public void deleteAlarms(List<SingleAlarmModel> singleAlarms) {
        for (SingleAlarmModel singleAlarm : singleAlarms) {
            singleAlarmDataBase.deleteSingleAlarm(singleAlarm);
        }
    }

    public List<SingleAlarmModel> getActiveAlarms() {
        return singleAlarmDataBase.getActiveSingleAlarms();
    }
}
