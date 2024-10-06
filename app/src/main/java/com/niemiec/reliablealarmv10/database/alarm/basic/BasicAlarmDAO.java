package com.niemiec.reliablealarmv10.database.alarm.basic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.niemiec.reliablealarmv10.database.alarm.entity.basic.BasicAlarm;

@Dao
public interface BasicAlarmDAO {
    @Insert
    void insertBasicAlarm(BasicAlarm basicAlarm);

    @Query("SELECT * FROM basicalarm WHERE id = 1")
    BasicAlarm getBasicAlarm();

    @Update
    void updateBasicAlarm(BasicAlarm basicAlarm);
}
