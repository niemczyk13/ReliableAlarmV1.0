package com.niemiec.reliablealarmv10.activity.alarm.launch.main;

import android.content.Context;
import android.util.Log;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.activity.alarm.launch.vibration.AlarmClockVibrationManager;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import java.util.Calendar;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private AlarmClockVibrationManager alarmClockVibrationManager;
    private SingleAlarmEntity singleAlarm;

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
        singleAlarm = alarmDataBase.getAlarm(id);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        int hour = singleAlarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = singleAlarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        if (singleAlarm.nap.isActive()) {
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
    public void onNapButtonClick() {
        stopAlarm();
        singleAlarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, singleAlarm.nap.getNextNapTime());
        alarmDataBase.updateAlarm(singleAlarm);
        AlarmManagerManagement.startAlarm(singleAlarm, context);
        view.updateNotification(alarmDataBase.getActiveAlarms());
    }

    @Override
    public void onTurnOffButtonClick() {
        stopAlarm();
        singleAlarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, -singleAlarm.nap.getTheSumOfTheNapTimes());
        singleAlarm.nap.resetNapsCount();
        startNewAlarmOrSetNoActive();
        view.updateNotification(alarmDataBase.getActiveAlarms());
    }

    @Override
    public void onPowerKeyClick() {
        onTurnOffButtonClick();
    }

    private void stopAlarm() {
        alarmClockAudioManager.stopAlarm();
        alarmClockVibrationManager.stopVibration();
    }

    private void startNewAlarmOrSetNoActive() {
        if (singleAlarm.alarmDateTime.isSchedule()) {
            AlarmDateTime adt = AlarmDateTimeUpdater.update(singleAlarm.alarmDateTime);
            singleAlarm.alarmDateTime = adt;
            AlarmManagerManagement.startAlarm(singleAlarm, context);
        } else {
            singleAlarm.isActive = false;
        }
        alarmDataBase.updateAlarm(singleAlarm);
    }

}
