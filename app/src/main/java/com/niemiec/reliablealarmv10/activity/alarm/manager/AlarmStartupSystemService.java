package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;
import java.util.List;

public class AlarmStartupSystemService extends Service {
    private AlarmDataBase alarmDataBase;
    public AlarmStartupSystemService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmDataBase = AlarmDataBase.getInstance(getApplicationContext());
        
    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO: //pobieramy teraźniejszą datę
        //        //z bazy pobieramy alarmy, które miały taką datę wywołania
        //        //usuwamy je z AlarmManagera
        //        //jeżeli nie mają harmonogramu to alarm ustawiamy na wyłączony
        Calendar now = Calendar.getInstance();
        List<Alarm> alarms = alarmDataBase.getAlarmsBefore(now);
        for (Alarm alarm : alarms) {
            AlarmManagerManagement.stopAlarm(alarm, getApplicationContext());
            if (!alarm.alarmDateTime.isSchedule()) {
                alarm.isActive = false;
            }
        }
        return null;
    }
}