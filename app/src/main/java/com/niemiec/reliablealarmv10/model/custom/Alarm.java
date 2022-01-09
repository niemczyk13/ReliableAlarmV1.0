package com.niemiec.reliablealarmv10.model.custom;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.risingview.model.RisingSound;

import java.util.Calendar;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @Embedded
    public AlarmDateTime alarmDateTime;
    @Embedded
    public Sound sound;
    @Embedded
    public Nap nap;
    @Embedded
    public RisingSound risingSound;
    public int volume;
    public boolean vibration;
    public String name;
    public String note;
    public boolean isActive;

    public int compareTimeTo(Alarm alarm) {
        int thisHour = alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int thisMinute = alarmDateTime.getDateTime().get(Calendar.MINUTE);
        int hour = alarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = alarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        if (thisHour < hour)
            return -1;
        else if (thisHour > hour)
            return 1;
        else if (thisMinute < minute)
            return -1;
        else if (thisMinute > minute)
            return 1;
        else
            return 0;
    }

    public int compareDateTimeTo(Alarm alarm) {
        return this.alarmDateTime.getDateTime().compareTo(alarm.alarmDateTime.getDateTime());
    }
}
