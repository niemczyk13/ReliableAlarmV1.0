package com.niemiec.reliablealarmv10.database.alarm.custom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;

import java.util.List;

@Dao
public interface GroupAlarmDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGroupAlarm(GroupAlarmEntity groupAlarmEntity);

    @Insert
    void insertSingleAlarms(List<SingleAlarmEntity> singleAlarmEntities);

    @Query("SELECT * FROM SingleAlarmEntity WHERE groupAlarmId = :groupId")
    List<SingleAlarmEntity> getAlarmsByGroupId(long groupId);

    //TODO @Query
   // void insertSingleAlarm(SingleAlarmEntity singleAlarm);

    @Query("SELECT * FROM GroupAlarmEntity WHERE id = :id")
    GroupAlarmEntity getGroupAlarmById(long id);

    //TODO @Query
    //void deleteGroupAlarm(GroupAlarmModel groupAlarmModel);

    //TODO @Query
    //void deleteSingleAlarm(SingleAlarmEntity singleAlarm);

    //TODO @Query
    //void updateGroupAlarm(GroupAlarmModel groupAlarmModel);
}
