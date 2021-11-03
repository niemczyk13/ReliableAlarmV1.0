package com.niemiec.reliablealarmv10.model.basic;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmsoundview.model.Sound;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.nap.NapValue;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.risingview.model.RisingSound;
import com.niemiec.risingview.view.RisingSoundValue;

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
        sound.setId(com.example.alarmsoundview.R.raw.closer);
        sound.setName("Pierwsza");

        nap = new Nap();
        nap.setTime(NapValue.FIRST.getValue());

        risingSound = new RisingSound();
        risingSound.setTime(RisingSoundValue.SECOND.getValue());

        volume = 80;
        vibration = false;
    }

    public Alarm getAlarm() {
        //TODO
        //tworzy nowy alarm, dodaje właściwości
        //tworzy teraźniejszy czas i jutrzejszą datę
        return null;
    }
}
