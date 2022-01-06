package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.niemiec.reliablealarmv10.activity.main.MainActivity;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.RequiresApi;

public class AlarmStartupSystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        AlarmDataBase dataBase = AlarmDataBase.getInstance(context);

//        Toast.makeText(context.getApplicationContext(), "URUCHAMIAM SERVICE" + dataBase.getAlarm(1).isActive, Toast.LENGTH_LONG).show();
        Toast.makeText(context, "URUCHAMIAM SERVICE", Toast.LENGTH_LONG).show();

//        Intent main = new Intent(context, MainActivity.class);
//        context.startActivity(main);
//        Intent alarmStartupSystemService = new Intent(context, AlarmStartupSystemService.class);
//        context.startService(alarmStartupSystemService);
//        context.startService(alarmStartupSystemService);
//        context.startForegroundService(alarmStartupSystemService);
//        AlarmDataBase alarmDataBase = AlarmDataBase.getI
//        nstance(context.getApplicationContext());
//        Toast.makeText(context.getApplicationContext(), "URUCHAMIAM SERVICE " + alarmDataBase, Toast.LENGTH_LONG).show();
//        List<Alarm> alarms = alarmDataBase.getActiveAlarms();
//        Toast.makeText(context.getApplicationContext(), "URUCHAMIAM SERVICE " + alarms.size(), Toast.LENGTH_LONG).show();

//        Calendar now = Calendar.getInstance();
//
//        for (Alarm alarm : alarms) {
//            if (!alarm.alarmDateTime.getDateTime().after(now)) {
//                if (!alarm.alarmDateTime.isSchedule()) {
//                    alarm.isActive = false;
//                    alarmDataBase.updateAlarm(alarm);
//                } else {
//                    //TODO
//                }
//            }
//        }

    }
}