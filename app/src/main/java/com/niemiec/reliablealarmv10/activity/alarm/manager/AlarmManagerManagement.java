package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AlarmManagerManagement {

    public static void startAlarm(Alarm alarm, Context context) {
        PendingIntent sender = createAlarmReceiverPendingIntent(alarm, context);
        addToAlarmManager(alarm, sender, context);
    }

    private static PendingIntent createAlarmReceiverPendingIntent(Alarm alarm, Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        System.out.println("ALARM ID: " + alarm.id);
        bundle.putLong("id", alarm.id);
        intent.putExtra("data", bundle);
        return PendingIntent.getBroadcast(context, (int) alarm.id, intent, 0);
    }

    private static void addToAlarmManager(Alarm alarm, PendingIntent sender, Context context) {
        Calendar now2 = Calendar.getInstance();
        now2.add(Calendar.SECOND, 3);

        long alarmTime = alarm.alarmDateTime.getDateTime().getTimeInMillis();
        long now = Calendar.getInstance().getTimeInMillis();

        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC_WAKEUP, now2.getTimeInMillis(), sender);

        if (alarmTime > now) {
            mgr.set(AlarmManager.RTC_WAKEUP, alarm.alarmDateTime.getDateTime().getTimeInMillis(), sender);
        }
    }


    public static void stopAlarm(Alarm alarm, Context context) {
        PendingIntent sender = createAlarmReceiverPendingIntent(alarm, context);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            mgr.cancel(sender);
        } catch (RuntimeException exception) {

        }
    }
}
