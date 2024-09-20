package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.niemiec.reliablealarmv10.database.alarm.basic.BasicAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.AlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmWithSingleAlarmsDAO;
import com.niemiec.reliablealarmv10.database.alarm.model.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmWithSingleAlarms;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

@Database(
        entities = {
                SingleAlarmEntity.class,
                BasicAlarm.class,
                GroupAlarmEntity.class
        }, version = 6, exportSchema = false)
@TypeConverters({Converts.class})
public abstract class AlarmDataBaseModel extends RoomDatabase {
    private static AlarmDataBaseModel instance = null;
    public abstract AlarmDAO alarmDAO();
    public abstract BasicAlarmDAO basicAlarmDAO();
    public abstract GroupAlarmDAO groupAlarmDAO();
    public abstract GroupAlarmWithSingleAlarmsDAO groupAlarmWithSingleAlarmsDAO();

    public static AlarmDataBaseModel getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AlarmDataBaseModel.class, "alarmDataBase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
}
