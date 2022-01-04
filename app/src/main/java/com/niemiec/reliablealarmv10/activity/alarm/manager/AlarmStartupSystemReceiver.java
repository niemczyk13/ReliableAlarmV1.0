package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmStartupSystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "URUCHAMIAM SERVICE", Toast.LENGTH_LONG).show();
        Intent alarmStartupSystemService = new Intent(context, AlarmStartupSystemService.class);
        context.startService(alarmStartupSystemService);
    }
}