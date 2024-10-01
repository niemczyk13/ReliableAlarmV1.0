package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.List;

@Dao
public interface SingleAlarmDAO {
    @Insert
    void insertAlarm(SingleAlarmEntity singleAlarm);

    @Query("SELECT * FROM SingleAlarmEntity")
    List<SingleAlarmEntity> getAll();

    @Query("SELECT * FROM SingleAlarmEntity WHERE id = :id")
    SingleAlarmEntity getAlarm(long id);

    @Update
    void updateAlarm(SingleAlarmEntity singleAlarm);

    @Delete
    void deleteAlarm(SingleAlarmEntity singleAlarm);

    @Query("SELECT * FROM SingleAlarmEntity WHERE dateTime <= :dateInMillis")
    List<SingleAlarmEntity> getAlarmsBefore(long dateInMillis);

    @Query("SELECT * FROM SingleAlarmEntity ORDER BY id DESC LIMIT 1")
    SingleAlarmEntity getLastAlarm();

    @Query("SELECT * FROM SingleAlarmEntity WHERE isActive = 1")
    List<SingleAlarmEntity> getActiveAlarms();
}
