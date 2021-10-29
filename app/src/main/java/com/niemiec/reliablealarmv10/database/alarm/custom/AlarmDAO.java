package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert
    void insertAlarm(Alarm alarm);

    @Query("SELECT * FROM Alarm")
    List<Alarm> getAll();

    @Query("SELECT * FROM Alarm WHERE id = :id")
    Alarm getAlarm(long id);
}
