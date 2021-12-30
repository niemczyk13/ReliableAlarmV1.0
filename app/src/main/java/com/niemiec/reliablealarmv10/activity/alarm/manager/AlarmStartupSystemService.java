package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;

public class AlarmStartupSystemService extends Service {
    private AlarmDataBase alarmDataBase;
    private AlarmManager alarmManager;
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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}