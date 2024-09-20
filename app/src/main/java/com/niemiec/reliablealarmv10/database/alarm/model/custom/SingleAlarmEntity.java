package com.niemiec.reliablealarmv10.database.alarm.model.custom;

import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;
import com.niemiec.risingview.model.RisingSound;

import java.util.Calendar;

@Entity
public class SingleAlarmEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long groupAlarmId;
    @Embedded
    public AlarmDateTime alarmDateTime;
    @Embedded
    public Sound sound;
    @Embedded
    public Nap nap;
    @Embedded
    public RisingSound risingSound;
    @Embedded
    public SafeAlarmLaunch safeAlarmLaunch;
    public int volume;
    public boolean vibration;
    public String name;
    public String note;
    public boolean isActive;

    public int compareTimeTo(SingleAlarmEntity singleAlarm) {
        int thisHour = alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int thisMinute = alarmDateTime.getDateTime().get(Calendar.MINUTE);
        int hour = singleAlarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = singleAlarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
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

    public int compareDateTimeTo(SingleAlarmEntity singleAlarm) {
        return this.alarmDateTime.getDateTime().compareTo(singleAlarm.alarmDateTime.getDateTime());
    }
}
