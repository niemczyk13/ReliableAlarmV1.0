package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data;

import android.os.Build;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmListComponent {
    private List<Alarm> alarms;
    private List<Boolean> selected;

    public AlarmListComponent(List<Alarm> alarms) {
        this.alarms = alarms.stream().sorted(Alarm::compareTimeTo).collect(Collectors.toList());
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

    public List<Alarm> concat(AlarmListComponent alc) {
        return Stream.concat(alarms.stream(), alc.getAlarms().stream()).collect(Collectors.toList());
    }
}
