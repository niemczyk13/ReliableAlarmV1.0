package com.niemiec.reliablealarmv10.activity.main.alarm.list;

import android.os.Build;

import com.example.alarmschedule.view.alarm.schedule.text.DateTextGenerator;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;

public class AlarmsList {
    private List<Alarm> alarms;
    private List<Boolean> select;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public AlarmsList(List<Alarm> alarms) {
        this.alarms = alarms.stream()
                .sorted((Alarm::compareTimeTo))
                .collect(Collectors.toList());

        this.select = new ArrayList<>();

        this.alarms.forEach(a -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            System.out.println(timeFormat.format(a.alarmDateTime.getDateTime().getTime()));
        });
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
