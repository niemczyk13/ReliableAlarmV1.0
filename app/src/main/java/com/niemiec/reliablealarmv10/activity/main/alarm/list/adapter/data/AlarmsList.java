package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data;

import android.os.Build;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmsList {
    private List<Alarm> alarms;
    private AlarmListComponent activeAlarms;
    private AlarmListComponent inactiveAlarms;

    public AlarmsList(List<Alarm> alarms) {
        activeAlarms = new AlarmListComponent(alarms.stream().filter(alarm -> alarm.isActive).collect(Collectors.toList()));
        inactiveAlarms = new AlarmListComponent(alarms.stream().filter(alarm -> !alarm.isActive).collect(Collectors.toList()));
        connectAlarmsList();
    }

    private void connectAlarmsList() {
        alarms = activeAlarms.concat(inactiveAlarms);
    }

    //TODO
    public List<Alarm> getAlarms() {
        return alarms;
    }

    public Alarm get(int index) {
        return alarms.get(index);
    }


}
