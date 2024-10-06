package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.niemiec.reliablealarmv10.database.alarm.basic.BasicAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.SingleAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmWithSingleAlarmsDAO;
import com.niemiec.reliablealarmv10.database.alarm.entity.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

@Database(
        entities = {
                SingleAlarmEntity.class,
                BasicAlarm.class,
                GroupAlarmEntity.class
        }, version = 7, exportSchema = false)
@TypeConverters({Converts.class})
public abstract class AlarmDataBaseModel extends RoomDatabase {
    private static AlarmDataBaseModel instance = null;
    public abstract SingleAlarmDAO singleAlarmDAO();
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
