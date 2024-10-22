package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data;

import android.os.Build;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmsList {
    private List<SingleAlarmModel> singleAlarms;
    private List<Boolean> selected;

    public AlarmsList(List<SingleAlarmModel> singleAlarms) {
        createAlarmsList(singleAlarms);
        createSelectedAlarmsList();
    }

    private void createSelectedAlarmsList() {
        selected = new ArrayList<>();
        for (int i = 0; i < singleAlarms.size(); i++) {
            selected.add(false);
        }
    }

    private void createAlarmsList(List<SingleAlarmModel> singleAlarms) {
        List<SingleAlarmModel> activeSingleAlarms = singleAlarms.stream().filter(alarm -> alarm.isActive()).sorted(SingleAlarmModel::compareDateTimeTo).collect(Collectors.toList());
        List<SingleAlarmModel> inactiveSingleAlarms = singleAlarms.stream().filter(alarm -> !alarm.isActive()).sorted(SingleAlarmModel::compareTimeTo).collect(Collectors.toList());
        this.singleAlarms = Stream.concat(activeSingleAlarms.stream(), inactiveSingleAlarms.stream()).collect(Collectors.toList());
    }

    public List<SingleAlarmModel> getAlarms() {
        return singleAlarms;
    }

    public SingleAlarmModel get(int index) {
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

    public List<SingleAlarmModel> getSelectedAlarms() {
        List<SingleAlarmModel> selectedSingleAlarms = new ArrayList<>();
        for (int i = 0; i < singleAlarms.size(); i++) {
            if (selected.get(i)) {
                selectedSingleAlarms.add(singleAlarms.get(i));
            }
        }
        return selectedSingleAlarms;
    }
}
