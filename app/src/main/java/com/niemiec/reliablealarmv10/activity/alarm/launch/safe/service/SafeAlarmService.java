package com.niemiec.reliablealarmv10.activity.alarm.launch.safe.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Toast;

import com.niemiec.reliablealarmv10.activity.alarm.launch.safe.SafeAlarmActivity;

import androidx.annotation.Nullable;

public class SafeAlarmService extends IntentService {
    private static final long TWENTY_MINUTES = 20 * 60 * 1000;
    private static final long TEN_MINUTES = 10 * 60 * 1000;
    private static final long FIVE_MINUTES = 5 * 60 * 1000;
    private static final long THREE_MINUTES = 3 * 60 * 1000;
    private static final long TWO_MINUTES = 2 * 60 * 1000;
    private static final long ONE_MINUTE = 1 * 60 * 1000;
    private static final int FIRST_LEVEL = 85;
    private static final int SECOND_LEVEL = 65;
    private static final int THIRD_LEVEL = 55;
    private static final int FOURTH_LEVEL = 40;
    private static final int FIFTH_LEVEL = 30;
    private long alarmId;
    private int percentageSafeAlarmValue;
    private int batteryPercentageValue;
    private long waitTime;

    public SafeAlarmService() {
        super("SafeAlarmActivity");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getValuesFromIntent(intent);
        boolean wait = true;
        System.out.println("TIIIIII");
        waitTime = 3000;
        while (wait) {
            synchronized (this) {
                try {
                    updateActualBatteryPercentageValue();
                    if (batteryPercentageValue <= percentageSafeAlarmValue) {
//                        //TODO wywołać aktywność SafeAlarmActivity
                        Intent safeAlarm = new Intent(getApplicationContext(), SafeAlarmActivity.class);
                        safeAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putLong("id", alarmId);
                        safeAlarm.putExtra("data", bundle);
                        getApplication().startActivity(safeAlarm);
    //
    //                        //TODO zakończyć tę usługę
                        wait = false;
                    } else {
                        setWaitTime();

                        wait(waitTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateActualBatteryPercentageValue() {
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        batteryPercentageValue = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    private void getValuesFromIntent(Intent intent) {
        Bundle bundle = intent.getBundleExtra("data");
        alarmId = bundle.getLong("id");
        percentageSafeAlarmValue = bundle.getInt("percentage_safe_alarm_value");
    }

    private void setWaitTime() {
        updateActualBatteryPercentageValue();
        if (batteryPercentageValue > FIRST_LEVEL) {
            waitTime = TWENTY_MINUTES;
        } else if (batteryPercentageValue > SECOND_LEVEL) {
            waitTime = TEN_MINUTES;
        } else if (batteryPercentageValue > THIRD_LEVEL) {
            waitTime = FIVE_MINUTES;
        } else if (batteryPercentageValue > FOURTH_LEVEL) {
            waitTime = THREE_MINUTES;
        } else if (batteryPercentageValue > FIFTH_LEVEL) {
            waitTime = TWO_MINUTES;
        } else {
            waitTime = ONE_MINUTE;
        }
    }
}