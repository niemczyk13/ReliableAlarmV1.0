package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

@Dao
public interface SingleAlarmDAO {
    @Insert
    long insertAlarm(SingleAlarmEntity singleAlarm);

    @Query("SELECT * FROM SingleAlarmEntity")
    List<SingleAlarmEntity> getAll();

    @Query("SELECT * FROM SingleAlarmEntity WHERE id = :id")
    SingleAlarmEntity getAlarm(long id);

    @Query("SELECT * FROM SingleAlarmEntity WHERE groupAlarmId = :groupId")
    List<SingleAlarmEntity> getSingleAlarmsByGroupAlarmId(long groupId);

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

    @Query("SELECT * FROM SingleAlarmEntity WHERE groupAlarmId IS NULL OR groupAlarmId <= 0")
    List<SingleAlarmEntity> getAllSingleAlarmsWithoutGroupId();
}
