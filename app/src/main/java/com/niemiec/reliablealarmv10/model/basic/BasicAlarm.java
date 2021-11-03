package com.niemiec.reliablealarmv10.model.basic;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.nap.NapValue;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
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
    public int volume;
    public boolean vibration;

    public BasicAlarm() {
        sound = new Sound();
        sound.setSoundId(com.example.alarmsoundview.R.raw.closer);
        sound.setSoundName("Pierwsza");
        sound.setPersonal(false);

        nap = new Nap();
        nap.setNapTime(NapValue.FIRST.getValue());

        risingSound = new RisingSound();
        risingSound.setRisingSoundTime(RisingSoundValue.SECOND.getValue());

        volume = 80;
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
        alarm.isActive = true;

        return alarm;
    }
}
