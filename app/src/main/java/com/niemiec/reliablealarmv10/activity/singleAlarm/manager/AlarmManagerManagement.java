package com.niemiec.reliablealarmv10.activity.singleAlarm.manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.singleAlarm.launch.safe.service.SafeAlarmService;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmManagerManagement {

    public static void startAlarm(SingleAlarm singleAlarm, Context context) {
        PendingIntent sender = createAlarmReceiverPendingIntent(singleAlarm, context);
        addToAlarmManager(singleAlarm, sender, context);
        startSafeAlarmService(singleAlarm, context);
        notifyAlarmNotification(context, sender);
    }




    private static PendingIntent createAlarmReceiverPendingIntent(SingleAlarm singleAlarm, Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", singleAlarm.id);
        intent.putExtra("data", bundle);
        return PendingIntent.getBroadcast(context, (int) singleAlarm.id, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private static void addToAlarmManager(SingleAlarm singleAlarm, PendingIntent sender, Context context) {
        Calendar now2 = Calendar.getInstance();
        now2.add(Calendar.SECOND, 3);

        long alarmTime = singleAlarm.alarmDateTime.getDateTime().getTimeInMillis();
        long now = Calendar.getInstance().getTimeInMillis();

        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC_WAKEUP, now2.getTimeInMillis(), sender);

        if (alarmTime > now) {
            mgr.set(AlarmManager.RTC_WAKEUP, singleAlarm.alarmDateTime.getDateTime().getTimeInMillis(), sender);
        }
    }

    public static void stopAlarm(SingleAlarm singleAlarm, Context context) {
        PendingIntent sender = createAlarmReceiverPendingIntent(singleAlarm, context);
        stopSafeAlarmService(singleAlarm, context);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            mgr.cancel(sender);
        } catch (RuntimeException exception) {

        }
    }

    private static void startSafeAlarmService(SingleAlarm singleAlarm, Context context) {
        if (singleAlarm.safeAlarmLaunch.isOn()) {
            context.startService(createSafeAlarmServiceIntent(singleAlarm, context));
        }
    }

    private static void notifyAlarmNotification(Context context, PendingIntent sender) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext()).setSmallIcon(R.drawable.ic_baseline_access_alarm_24);
        builder.setContentIntent(sender);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    private static Intent createSafeAlarmServiceIntent(SingleAlarm singleAlarm, Context context) {
        Intent intent = new Intent(context, SafeAlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", singleAlarm.id);
        bundle.putInt("percentage_safe_alarm_value", singleAlarm.safeAlarmLaunch.getSafeAlarmLaunchPercentage());
        intent.putExtra("data", bundle);
        return intent;
    }

    private static void stopSafeAlarmService(SingleAlarm singleAlarm, Context context) {
        if (singleAlarm.safeAlarmLaunch.isOn()) {
            context.stopService(createSafeAlarmServiceIntent(singleAlarm, context));
        }
    }

}
