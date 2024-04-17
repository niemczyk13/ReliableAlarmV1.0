package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data;

import android.os.Build;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmsList {
    private List<SingleAlarm> singleAlarms;
    private List<Boolean> selected;

    public AlarmsList(List<SingleAlarm> singleAlarms) {
        createAlarmsList(singleAlarms);
        createSelectedAlarmsList();
    }

    private void createSelectedAlarmsList() {
        selected = new ArrayList<>();
        for (int i = 0; i < singleAlarms.size(); i++) {
            selected.add(false);
        }
    }

    private void createAlarmsList(List<SingleAlarm> singleAlarms) {
        List<SingleAlarm> activeSingleAlarms = singleAlarms.stream().filter(alarm -> alarm.isActive).sorted(SingleAlarm::compareDateTimeTo).collect(Collectors.toList());
        List<SingleAlarm> inactiveSingleAlarms = singleAlarms.stream().filter(alarm -> !alarm.isActive).sorted(SingleAlarm::compareTimeTo).collect(Collectors.toList());
        this.singleAlarms = Stream.concat(activeSingleAlarms.stream(), inactiveSingleAlarms.stream()).collect(Collectors.toList());
    }

    public List<SingleAlarm> getAlarms() {
        return singleAlarms;
    }

    public SingleAlarm get(int index) {
        return singleAlarms.get(index);
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

    public List<SingleAlarm> getSelectedAlarms() {
        List<SingleAlarm> selectedSingleAlarms = new ArrayList<>();
        for (int i = 0; i < singleAlarms.size(); i++) {
            if (selected.get(i)) {
                selectedSingleAlarms.add(singleAlarms.get(i));
            }
        }
        return selectedSingleAlarms;
    }
}
