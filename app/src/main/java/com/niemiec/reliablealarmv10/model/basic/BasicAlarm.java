package com.niemiec.reliablealarmv10.model.basic;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.risingview.model.RisingSound;

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
}
