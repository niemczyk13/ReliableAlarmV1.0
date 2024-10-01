package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmWithSingleAlarms;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;

import java.util.List;

@Dao
public interface GroupAlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleAlarm(SingleAlarmEntity singleAlarmEntity);

    @Query("SELECT * FROM GroupAlarmEntity WHERE id = :id")
    GroupAlarmEntity getGroupAlarm(long id);

    @Query("SELECT * FROM SingleAlarmEntity WHERE groupAlarmId = :groupId")
    List<SingleAlarmEntity> getSingleAlarms(long groupId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSingleAlarm(SingleAlarmEntity singleAlarmEntity);

    @Delete
    void deleteGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Delete
    void deleteSingleAlarm(SingleAlarmEntity singleAlarmEntity);
}
