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
    private List<Boolean> selected;

    public AlarmsList(List<Alarm> alarms) {
        createAlarmsList(alarms);
        createSelectedAlarmsList();
    }

    private void createSelectedAlarmsList() {
        selected = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            selected.add(false);
        }
    }

    private void createAlarmsList(List<Alarm> alarms) {
        List<Alarm> activeAlarms = alarms.stream().filter(alarm -> alarm.isActive).sorted(Alarm::compareTimeTo).collect(Collectors.toList());
        List<Alarm> inactiveAlarms = alarms.stream().filter(alarm -> !alarm.isActive).sorted(Alarm::compareTimeTo).collect(Collectors.toList());
        this.alarms = Stream.concat(activeAlarms.stream(), inactiveAlarms.stream()).collect(Collectors.toList());
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    public Alarm get(int index) {
        return alarms.get(index);
    }

    public boolean isSelected(int index) {
        return selected.get(index);
    }

    public void checkOrUncheckAlarm(int index) {
        selected.set(index, !selected.get(index));
    }

    public void clearSelected() {
        createSelectedAlarmsList();
    }

    public List<Alarm> getSelectedAlarms() {
        List<Alarm> selectedAlarms = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            if (selected.get(i)) {
                selectedAlarms.add(alarms.get(i));
            }
        }
        return selectedAlarms;
    }
}
