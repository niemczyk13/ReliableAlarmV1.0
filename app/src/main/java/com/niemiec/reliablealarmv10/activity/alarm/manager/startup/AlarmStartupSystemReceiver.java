package com.niemiec.reliablealarmv10.activity.alarm.manager.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.Calendar;
import java.util.List;

public class AlarmStartupSystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SingleAlarmDataBase singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);

        List<SingleAlarmEntity> singleAlarms = singleAlarmDataBase.getActiveSingleAlarms();
        Toast.makeText(context.getApplicationContext(), "Odświeżono alarmy", Toast.LENGTH_LONG).show();

        Calendar now = Calendar.getInstance();
        for (SingleAlarmEntity singleAlarm : singleAlarms) {
            if (!singleAlarm.alarmDateTime.getDateTime().after(now)) {
                if (!singleAlarm.alarmDateTime.isSchedule()) {
                    singleAlarm.isActive = false;
                    singleAlarmDataBase.updateSingleAlarm(singleAlarm);
                } else {
                    //TODO
                    singleAlarm.alarmDateTime = AlarmDateTimeUpdater.update(singleAlarm.alarmDateTime);
                    singleAlarmDataBase.updateSingleAlarm(singleAlarm);
                    AlarmManagerManagement.startAlarm(singleAlarm, context);
                }
            } else {
                AlarmManagerManagement.startAlarm(singleAlarm, context);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AlarmNotificationManager.updateNotification(context.getApplicationContext(), singleAlarmDataBase.getActiveSingleAlarms());
        }
    }
}