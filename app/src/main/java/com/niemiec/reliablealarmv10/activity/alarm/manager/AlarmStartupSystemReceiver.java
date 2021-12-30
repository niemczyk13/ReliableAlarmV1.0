package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmStartupSystemReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmStartupSystemService = new Intent(context, AlarmStartupSystemService.class);
        context.startService(alarmStartupSystemService);
    }
}