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
    private AlarmsListBinder alarmsListBinder;
    private AlarmListComponent activeAlarms;
    private AlarmListComponent inactiveAlarms;

    public AlarmsList(List<Alarm> alarms) {
        activeAlarms = new AlarmListComponent(alarms.stream().filter(alarm -> alarm.isActive).collect(Collectors.toList()));
        inactiveAlarms = new AlarmListComponent(alarms.stream().filter(alarm -> !alarm.isActive).collect(Collectors.toList()));
        alarmsListBinder = new AlarmsListBinder(connectAlarmsList());
    }

    private List<Alarm> connectAlarmsList() {
        return activeAlarms.concat(inactiveAlarms);
    }

    //TODO
    public List<Alarm> getAlarms() {
        return alarmsListBinder.getAlarms();
    }

    public Alarm get(int index) {
        return alarmsListBinder.getAlarm(index);
    }


}
