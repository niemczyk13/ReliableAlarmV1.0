package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.launch.safe.service.SafeAlarmService;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AlarmManagerManagement {

    public static void startAlarm(Alarm alarm, Context context) {
        PendingIntent sender = createAlarmReceiverPendingIntent(alarm, context);
        addToAlarmManager(alarm, sender, context);
        startSafeAlarmService(alarm, context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext()).setSmallIcon(R.drawable.ic_baseline_access_alarm_24);
        builder.setContentIntent(sender);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    private static PendingIntent createAlarmReceiverPendingIntent(Alarm alarm, Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
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
            stopSafeAlarmService(alarm, context);
            mgr.cancel(sender);
        } catch (RuntimeException exception) {

        }
    }

    private static void startSafeAlarmService(Alarm alarm, Context context) {
        if (alarm.safeAlarmLaunch.isOn()) {
            context.startService(createSafeAlarmServiceIntent(alarm, context));
        }
    }

    private static Intent createSafeAlarmServiceIntent(Alarm alarm, Context context) {
        Intent intent = new Intent(context, SafeAlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", alarm.id);
        bundle.putInt("percentage_safe_alarm_value", alarm.safeAlarmLaunch.getSafeAlarmLaunchPercentage());
        intent.putExtra("data", bundle);
        return intent;
    }

    private static void stopSafeAlarmService(Alarm alarm, Context context) {
        if (alarm.safeAlarmLaunch.isOn()) {
            context.stopService(createSafeAlarmServiceIntent(alarm, context));
        }
    }

}
