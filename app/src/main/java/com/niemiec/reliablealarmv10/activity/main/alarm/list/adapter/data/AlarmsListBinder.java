package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmsListBinder {
    private List<Alarm> alarms;
    private List<Boolean> selected;

    public AlarmsListBinder(List<Alarm> alarms) {
        this.alarms = alarms;
        createSelectedList();
    }

    private void createSelectedList() {
        selected = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            selected.add(false);
        }
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public Alarm getAlarm(int index) {
        return alarms.get(index);
    }
}
