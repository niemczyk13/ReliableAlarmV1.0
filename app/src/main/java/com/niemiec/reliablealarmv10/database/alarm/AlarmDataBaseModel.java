package com.niemiec.reliablealarmv10.database.alarm;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.niemiec.reliablealarmv10.database.alarm.basic.BasicAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.AlarmDAO;
import com.niemiec.reliablealarmv10.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

@Database(entities = {Alarm.class, BasicAlarm.class}, version = 2, exportSchema = false)
@TypeConverters({Converts.class})
public abstract class AlarmDataBaseModel extends RoomDatabase {
    public abstract AlarmDAO alarmDAO();
    public abstract BasicAlarmDAO basicAlarmDAO();
}
