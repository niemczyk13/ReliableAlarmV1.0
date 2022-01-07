package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update
    void updateAlarm(Alarm alarm);

    @Delete
    void deleteAlarm(Alarm alarm);

    @Query("SELECT * FROM Alarm WHERE dateTime <= :dateInMillis")
    List<Alarm> getAlarmsBefore(long dateInMillis);

    @Query("SELECT * FROM Alarm ORDER BY id DESC LIMIT 1")
    Alarm getLastAlarm();

    @Query("SELECT * FROM Alarm WHERE isActive = 1")
    List<Alarm> getActiveAlarms();
}
