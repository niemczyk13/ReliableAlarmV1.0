package com.niemiec.reliablealarmv10.activity.singleAlarm.launch.safe;

import android.content.Context;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.singleAlarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.activity.singleAlarm.launch.vibration.AlarmClockVibrationManager;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarm;

import java.util.Calendar;

public class SafeAlarmPresenter extends BasePresenter<SafeAlarmContractMVP.View> implements SafeAlarmContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private AlarmClockVibrationManager alarmClockVibrationManager;
    private SingleAlarm singleAlarm;

    public SafeAlarmPresenter(Context context) {
        super();
        this.context = context;
        alarmDataBase = AlarmDataBase.getInstance(context);
        alarmClockAudioManager = new AlarmClockAudioManager(context);
        alarmClockVibrationManager = new AlarmClockVibrationManager(context);
    }

    @Override
    public void initView(long id) {
        singleAlarm = alarmDataBase.getAlarm(id);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        showAlarmClock();
        showActualClock();
        showPercentageInformation();
    }

    private void showAlarmClock() {
        int hour = singleAlarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = singleAlarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        view.showAlarmClock(hour, minute);
    }

    private void showActualClock() {
        Calendar calendar = Calendar.getInstance();
        view.showActualClock(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    private void showPercentageInformation() {
        view.showBatteryPercentageInfo(context.getResources().getString(R.string.left_word) + " " + singleAlarm.safeAlarmLaunch.getSafeAlarmLaunchPercentage() + "% " + context.getResources().getString(R.string.battery_word));
    }

    private void callUpAlarm() {
        turnOnAlarmSound();
        turnOnVibration();
    }

    private void turnOnAlarmSound() {
        if (singleAlarm.risingSound.isOn()) {
            alarmClockAudioManager.startRisingAlarm(singleAlarm.sound, singleAlarm.volume, singleAlarm.risingSound.getTimeInMilliseconds());
        } else {
            alarmClockAudioManager.startAlarm(singleAlarm.sound, singleAlarm.volume);
        }
    }

    private void turnOnVibration() {
        alarmClockVibrationManager.startVibration(singleAlarm.vibration);
    }

    @Override
    public void onOkButtonClick() {
        stopAlarm();
        view.closeActivity();
    }

    private void stopAlarm() {
        alarmClockAudioManager.stopAlarm();
        alarmClockVibrationManager.stopVibration();
    }
}
