package com.niemiec.reliablealarmv10.activity.singleAlarm.manager.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.text.SimpleDateFormat;
import java.util.List;

//TODO przekazujemy tylko listę aktywnych alarmów - i w tej klasie samo określa się czy wyświetlić coś,
//TODO zaktualizwoać czy usunąć notification
public class AlarmNotificationManager {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "My Notification";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void updateNotification(Context context, List<SingleAlarm> singleAlarms) {
        if (singleAlarms.isEmpty()) {
            cancelNotification(context);
        } else {
            String title = getTheDateOfTheNextAlarm(singleAlarms);
            showNotification(context, title, "Alarm jest włączony");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String getTheDateOfTheNextAlarm(List<SingleAlarm> singleAlarms) {
        SingleAlarm singleAlarm = singleAlarms.stream().sorted((o1, o2) -> o1.alarmDateTime.getDateTime().compareTo(o2.alarmDateTime.getDateTime())).findFirst().get();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(singleAlarm.alarmDateTime.getDateTime().getTime());
    }


    private static void showNotification(Context context, String title, String text) {
        createNotificationChannel(context);
        NotificationCompat.Builder builder = createNotificationBuilder(context, title, text);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context.getApplicationContext());
        try {
            managerCompat.notify(NOTIFICATION_ID, builder.build());
        } catch (SecurityException e) {
            //TODO
        }
    }

    private static NotificationCompat.Builder createNotificationBuilder(Context context, String title, String text) {
        return new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);
    }

    private static void createNotificationChannel(Context context) {
        NotificationChannel channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private static void cancelNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.cancel(NOTIFICATION_ID);
        }
    }
}