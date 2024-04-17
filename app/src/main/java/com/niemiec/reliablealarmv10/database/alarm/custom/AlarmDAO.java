package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Insert
    void insertAlarm(SingleAlarm singleAlarm);

    @Query("SELECT * FROM SingleAlarm")
    List<SingleAlarm> getAll();

    @Query("SELECT * FROM SingleAlarm WHERE id = :id")
    SingleAlarm getAlarm(long id);

    @Update
    void updateAlarm(SingleAlarm singleAlarm);

    @Delete
    void deleteAlarm(SingleAlarm singleAlarm);

    @Query("SELECT * FROM SingleAlarm WHERE dateTime <= :dateInMillis")
    List<SingleAlarm> getAlarmsBefore(long dateInMillis);

    @Query("SELECT * FROM SingleAlarm ORDER BY id DESC LIMIT 1")
    SingleAlarm getLastAlarm();

    @Query("SELECT * FROM SingleAlarm WHERE isActive = 1")
    List<SingleAlarm> getActiveAlarms();
}
