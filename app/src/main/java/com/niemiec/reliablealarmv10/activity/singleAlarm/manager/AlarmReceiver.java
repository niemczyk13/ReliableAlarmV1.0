package com.niemiec.reliablealarmv10.activity.singleAlarm.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.activity.singleAlarm.launch.main.AlarmClockActivity;
@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra("data");

        Intent intent1 = new Intent(context, AlarmClockActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("data", bundle);
        context.startActivity(intent1);
    }
}
