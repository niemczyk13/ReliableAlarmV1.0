package com.niemiec.reliablealarmv10.model.custom;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.risingview.model.RisingSound;

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

}
