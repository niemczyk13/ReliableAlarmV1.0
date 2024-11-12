package com.niemiec.reliablealarmv10.fragment.alarm.list.list;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmsList {
    private List<Alarm> alarms;
    private List<Boolean> alarmsSelected;

    public AlarmsList(List<Alarm> allAlarms) {
        createAlarmsList(allAlarms);
        createSelectedAlarmsList();
    }

    private void createAlarmsList(List<Alarm> alarms) {
        List<Alarm> activeAlarms = alarms.stream()
                .filter(alarm -> alarm.isActive())
                .sorted(Alarm::compareDateTimeTo)
                .collect(Collectors.toList());
        List<Alarm> inactiveAlarms = alarms.stream()
                .filter(alarm -> !alarm.isActive())
                .sorted(Alarm::compareTimeTo)
                .collect(Collectors.toList());
        this.alarms = Stream.concat(activeAlarms.stream(), inactiveAlarms.stream())
                .collect(Collectors.toList());
    }

    private void createSelectedAlarmsList() {
        alarmsSelected = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            alarmsSelected.add(false);
        }
    }

    public Alarm get(int index) {
        return alarms.get(index);
    }

    public boolean isSelected(int index) {
        return alarmsSelected.get(index);
    }

    public void checkOrUncheckAlarm(int index) {
        alarmsSelected.set(index, !alarmsSelected.get(index));
    }

    public void clearSelected() {
        createSelectedAlarmsList();
    }

    public List<Alarm> getSelectedAlarms() {
        List<Alarm> selectedAlarms = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            if (alarmsSelected.get(i)) {
                selectedAlarms.add(alarms.get(i));
            }
        }
        return selectedAlarms;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
