package com.niemiec.reliablealarmv10.database.alarm.basic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarmSettings;

@Dao
public interface BasicAlarmDAO {
    @Insert
    void insertBasicAlarm(BasicAlarmSettings basicAlarmSettings);

    @Query("SELECT * FROM BasicAlarmSettings WHERE id = 1")
    BasicAlarmSettings getBasicAlarm();

    @Update
    void updateBasicAlarm(BasicAlarmSettings basicAlarmSettings);
}
