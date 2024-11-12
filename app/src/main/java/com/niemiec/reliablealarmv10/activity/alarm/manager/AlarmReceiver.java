package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.activity.alarm.launch.main.AlarmClockActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra(BundleNames.DATA.name());

        Intent intent1 = new Intent(context, AlarmClockActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra(BundleNames.DATA.name(), bundle);
        context.startActivity(intent1);
    }
}
