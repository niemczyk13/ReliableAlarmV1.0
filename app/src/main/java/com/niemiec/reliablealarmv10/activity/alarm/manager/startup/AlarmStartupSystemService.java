package com.niemiec.reliablealarmv10.activity.alarm.manager.startup;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmStartupSystemService extends Service {
//    private AlarmDataBase alarmDataBase;
    public AlarmStartupSystemService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        alarmDataBase = AlarmDataBase.getInstance(getApplicationContext());
//        Toast.makeText(getApplicationContext(), "URUCHAMIAM SERVICE 232323", Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "JESTEM! 3333333", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "JESTEM! 11111", Toast.LENGTH_LONG).show();
        // TODO: //pobieramy teraźniejszą datę
        //        //z bazy pobieramy alarmy, które miały taką datę wywołania
        //        //usuwamy je z AlarmManagera
        //        //jeżeli nie mają harmonogramu to alarm ustawiamy na wyłączony
//        Calendar now = Calendar.getInstance();
//        List<Alarm> alarms = alarmDataBase.getAlarmsBefore(now);
//        for (Alarm alarm : alarms) {
//            AlarmManagerManagement.stopAlarm(alarm, getApplicationContext());
//            if (!alarm.alarmDateTime.isSchedule()) {
//                alarm.isActive = false;
//                alarmDataBase.updateAlarm(alarm);
//            } else {
//                //TODO ustawiamy nową datę alarmu
//            }
//        }
        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Toast.makeText(this, "JESTEM! 22222", Toast.LENGTH_LONG).show();
        // TODO: //pobieramy teraźniejszą datę
        //        //z bazy pobieramy alarmy, które miały taką datę wywołania
        //        //usuwamy je z AlarmManagera
        //        //jeżeli nie mają harmonogramu to alarm ustawiamy na wyłączony
//        Calendar now = Calendar.getInstance();
//        List<Alarm> alarms = alarmDataBase.getAlarmsBefore(now);
//        for (Alarm alarm : alarms) {
//            AlarmManagerManagement.stopAlarm(alarm, getApplicationContext());
//            if (!alarm.alarmDateTime.isSchedule()) {
//                alarm.isActive = false;
//                alarmDataBase.updateAlarm(alarm);
//            } else {
//                //TODO ustawiamy nową datę alarmu
//            }
//        }

        //TODO ewentualne ponowne dodatnie do alarmmanagera pozostałych alarmów
        return null;
    }
}