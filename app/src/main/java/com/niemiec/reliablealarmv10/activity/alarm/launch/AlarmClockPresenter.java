package com.niemiec.reliablealarmv10.activity.alarm.launch;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.activity.alarm.launch.vibration.AlarmClockVibrationManager;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

import androidx.annotation.RequiresApi;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private AlarmClockVibrationManager alarmClockVibrationManager;
    private Alarm alarm;

    public AlarmClockPresenter(Context context) {
        super();
        this.context = context;
        alarmDataBase = AlarmDataBase.getInstance(context);
        alarmClockAudioManager = new AlarmClockAudioManager(context);
        alarmClockVibrationManager = new AlarmClockVibrationManager(context);
    }

    public void initView(Long id) {
        Log.println(Log.ASSERT, "", "ID: " + id);
        System.out.println("ID: " + id);
        alarm = alarmDataBase.getAlarm(id);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        int hour = alarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = alarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        if (alarm.nap.isActive()) {
            view.showAlarmClockWithNap(hour, minute);
        } else {
            view.showAlarmClockWithoutNap(hour, minute);
        }
    }

    private void callUpAlarm() {
        turnOnTheAlarmSound();
        turnOnVibration();
    }

    private void turnOnTheAlarmSound() {
        if (alarm.risingSound.isOn()) {
            alarmClockAudioManager.startRisingAlarm(alarm.sound, alarm.volume, alarm.risingSound.getTimeInMilliseconds());
        } else {
            alarmClockAudioManager.startAlarm(alarm.sound, alarm.volume);
        }
    }

    private void turnOnVibration() {
        alarmClockVibrationManager.startVibration(alarm.vibration);
    }

    @Override
    public void onNapButtonClick() {
        stopAlarm();

        alarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, alarm.nap.getNapTime());
        alarmDataBase.updateAlarm(alarm);
        AlarmManagerManagement.startAlarm(alarm, context);
        view.updateNotification(alarmDataBase.getActiveAlarms());
    }

    @Override
    public void onTurnOffButtonClick() {
        stopAlarm();
        startNewAlarmOrSetNoActive();
        view.updateNotification(alarmDataBase.getActiveAlarms());
    }

    private void stopAlarm() {
        alarmClockAudioManager.stopAlarm();
        alarmClockVibrationManager.stopVibration();
    }

    private void startNewAlarmOrSetNoActive() {
        if (alarm.alarmDateTime.isSchedule()) {
            AlarmDateTime adt = AlarmDateTimeUpdater.update(alarm.alarmDateTime);
            alarm.alarmDateTime = adt;
            AlarmManagerManagement.startAlarm(alarm, context);
        } else {
            alarm.isActive = false;
        }
        alarmDataBase.updateAlarm(alarm);
    }

}
