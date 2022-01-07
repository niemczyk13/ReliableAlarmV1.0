package com.niemiec.reliablealarmv10.activity.alarm.manager.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;
import java.util.List;

public class AlarmStartupSystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmDataBase alarmDataBase = AlarmDataBase.getInstance(context);

        List<Alarm> alarms = alarmDataBase.getActiveAlarms();
        Toast.makeText(context.getApplicationContext(), "Odświeżono alarmy", Toast.LENGTH_LONG).show();

        Calendar now = Calendar.getInstance();
        for (Alarm alarm : alarms) {
            if (!alarm.alarmDateTime.getDateTime().after(now)) {
                if (!alarm.alarmDateTime.isSchedule()) {
                    alarm.isActive = false;
                    alarmDataBase.updateAlarm(alarm);
                } else {
                    //TODO
                    alarm.alarmDateTime = AlarmDateTimeUpdater.update(alarm.alarmDateTime);
                    alarmDataBase.updateAlarm(alarm);
                    AlarmManagerManagement.startAlarm(alarm, context);
                }
            } else {
                AlarmManagerManagement.startAlarm(alarm, context);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AlarmNotificationManager.updateNotification(context.getApplicationContext(), alarmDataBase.getActiveAlarms());
        }
    }
}