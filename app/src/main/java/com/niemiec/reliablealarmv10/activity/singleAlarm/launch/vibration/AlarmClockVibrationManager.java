package com.niemiec.reliablealarmv10.activity.singleAlarm.launch.vibration;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class AlarmClockVibrationManager {
    private Vibrator vibrator;
    private boolean isVibrate = false;

    public AlarmClockVibrationManager(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void startVibration(boolean v) {
        isVibrate = true;
        if (v) {
            new Thread(() -> {
                do {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(800);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (isVibrate);

            }).start();
        }
    }

    public void stopVibration() {
        isVibrate = false;
        vibrator.cancel();
    }

}
