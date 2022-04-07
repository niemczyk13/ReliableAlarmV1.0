package com.niemiec.reliablealarmv10.model.basic;

import android.content.res.Resources;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.nap.NapValue;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchValue;
import com.niemiec.risingview.model.RisingSound;
import com.niemiec.risingview.view.RisingSoundValue;

import java.util.Calendar;

@Entity
public class BasicAlarm {
    @PrimaryKey(autoGenerate = true)
    public long id;
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

    public BasicAlarm() {
        int soundId = com.example.alarmsoundview.R.raw.closer;
        NapValue napValue = NapValue.FIRST;
        RisingSoundValue risingSoundValue = RisingSoundValue.SECOND;
        int volumeValue = 80;

        sound = new Sound();
        sound.setSoundId(soundId);
        //sound.setSoundName(Resources.getSystem().getResourceName(soundId));
        sound.setSoundName("Pierwsza");
        sound.setPersonal(false);

        safeAlarmLaunch.setSafeAlarmLaunchPercentage(SafeAlarmLaunchValue.SECOND.getValue());

        nap = new Nap();
        nap.setNapTime(napValue.getValue());

        risingSound = new RisingSound();
        risingSound.setRisingSoundTime(risingSoundValue.getValue());

        volume = volumeValue;
        vibration = false;
    }

    public Alarm getAlarm() {
        Alarm alarm = new Alarm();
        Calendar dateTime = Calendar.getInstance();
        dateTime.add(Calendar.DAY_OF_YEAR, 1);
        Week week = new Week();
        alarm.alarmDateTime = new AlarmDateTime(dateTime, week);
        alarm.sound = sound;
        alarm.nap = nap;
        alarm.risingSound = risingSound;
        alarm.volume = volume;
        alarm.vibration = vibration;
        alarm.name = "";
        alarm.note = "";
        alarm.isActive = true;
        alarm.safeAlarmLaunch = safeAlarmLaunch;
        return alarm;
    }
}
