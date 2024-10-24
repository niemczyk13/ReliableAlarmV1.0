package com.niemiec.reliablealarmv10.model.custom;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;

public interface Alarm {
    long getId();
    String getName();
    String getNote();
    boolean isActive();
    int compareTimeTo(Alarm alarm);
    int compareDateTimeTo(Alarm alarm);
    AlarmDateTime getAlarmDateTime();
}
