package com.niemiec.reliablealarmv10.activity.alarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.alarm.launch.main.AlarmClockActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra("data");

        Intent intent1 = new Intent(context, AlarmClockActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("data", bundle);
        context.startActivity(intent1);
        //TODO zktualizowanie listy alarmów - ten alarm juz jest neikatywny, jeżeli był jednorazowy, jezeli nie to ustalamy datę kolejnego alarmu
    }
}
