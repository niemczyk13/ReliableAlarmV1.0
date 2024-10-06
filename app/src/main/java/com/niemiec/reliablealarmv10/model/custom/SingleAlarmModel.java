package com.niemiec.reliablealarmv10.model.custom;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;
import com.niemiec.risingview.model.RisingSound;

import java.util.Calendar;

import lombok.Data;

@Data
public class SingleAlarmModel {
    private long id;
    private long groupAlarmId;
    private AlarmDateTime alarmDateTime;
    private Sound sound;
    private Nap nap;
    private RisingSound risingSound;
    private SafeAlarmLaunch safeAlarmLaunch;
    private int volume;
    private boolean vibration;
    private String name;
    private String note;
    private boolean isActive;

    public SingleAlarmModel(SingleAlarmEntity sa) {
        id = sa.id;
        groupAlarmId = sa.groupAlarmId;
        alarmDateTime = sa.alarmDateTime;
        sound = sa.sound;
        nap = sa.nap;
        risingSound = sa.risingSound;
        safeAlarmLaunch = sa.safeAlarmLaunch;
        volume = sa.volume;
        vibration = sa.vibration;
        name = sa.name;
        note = sa.note;
        isActive = sa.isActive;
    }

    public int compareTimeTo(SingleAlarmModel singleAlarm) {
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

    public int compareDateTimeTo(SingleAlarmModel singleAlarm) {
        return this.alarmDateTime.getDateTime().compareTo(singleAlarm.alarmDateTime.getDateTime());
    }
}
