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
    private static final int MINUTES_IN_HOUR = 60;

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
        int a1Hour = this.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        Integer a1Minute = this.alarmDateTime.getDateTime().get(Calendar.MINUTE);

        int a2Hour = alarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        Integer a2Minute = alarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);

        a1Minute += a1Hour * MINUTES_IN_HOUR;
        a2Minute += a2Hour * MINUTES_IN_HOUR;

        return a1Minute.compareTo(a2Minute);
    }
}
