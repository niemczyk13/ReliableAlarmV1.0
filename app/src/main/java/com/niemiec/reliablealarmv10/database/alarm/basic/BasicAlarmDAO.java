package com.niemiec.reliablealarmv10.database.alarm.basic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.niemiec.reliablealarmv10.model.basic.BasicAlarm;

@Dao
public interface BasicAlarmDAO {
    @Insert
    void insertBasiAlarm(BasicAlarm basicAlarm);

    @Query("SELECT * FROM basicalarm WHERE id = :id")
    BasicAlarm getBasicAlarm(long id);
}
