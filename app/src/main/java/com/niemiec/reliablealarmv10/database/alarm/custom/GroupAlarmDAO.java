package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;

import java.util.List;

@Dao
public interface GroupAlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Query("SELECT * FROM GroupAlarmEntity WHERE id = :id")
    GroupAlarmEntity getGroupAlarm(long id);

    @Query("SELECT * FROM GroupAlarmEntity")
    List<GroupAlarmEntity> getAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Delete
    void deleteGroupAlarm(GroupAlarmEntity groupAlarmEntity);
}
