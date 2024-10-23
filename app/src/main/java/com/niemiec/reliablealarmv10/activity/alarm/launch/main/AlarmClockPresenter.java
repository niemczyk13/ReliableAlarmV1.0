package com.niemiec.reliablealarmv10.activity.alarm.launch.main;

import android.content.Context;
import android.util.Log;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.activity.alarm.launch.vibration.AlarmClockVibrationManager;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.Calendar;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private Context context;
    private SingleAlarmDataBase singleAlarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private AlarmClockVibrationManager alarmClockVibrationManager;
    private SingleAlarmModel singleAlarm;

    public AlarmClockPresenter(Context context) {
        super();
        this.context = context;
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
        alarmClockAudioManager = new AlarmClockAudioManager(context);
        alarmClockVibrationManager = new AlarmClockVibrationManager(context);
    }

    public void initView(Long id) {
        Log.println(Log.ASSERT, "", "ID: " + id);
        System.out.println("ID: " + id);
        singleAlarm = singleAlarmDataBase.getSingleAlarm(id);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        int hour = singleAlarm.getAlarmDateTime().getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = singleAlarm.getAlarmDateTime().getDateTime().get(Calendar.MINUTE);
        if (singleAlarm.getNap().isActive()) {
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
        if (singleAlarm.getRisingSound().isOn()) {
            alarmClockAudioManager.startRisingAlarm(singleAlarm.getSound(), singleAlarm.getVolume(), singleAlarm.getRisingSound().getTimeInMilliseconds());
        } else {
            alarmClockAudioManager.startAlarm(singleAlarm.getSound(), singleAlarm.getVolume());
        }
    }

    private void turnOnVibration() {
        alarmClockVibrationManager.startVibration(singleAlarm.isVibration());
    }

    @Override
    public void onNapButtonClick() {
        stopAlarm();
        singleAlarm.getAlarmDateTime().getDateTime().add(Calendar.MINUTE, singleAlarm.getNap().getNextNapTime());
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
        AlarmManagerManagement.startAlarm(singleAlarm, context);
        view.updateNotification(singleAlarmDataBase.getActiveSingleAlarms());
    }

    @Override
    public void onTurnOffButtonClick() {
        stopAlarm();
        singleAlarm.getAlarmDateTime().getDateTime().add(Calendar.MINUTE, -singleAlarm.getNap().getTheSumOfTheNapTimes());
        singleAlarm.getNap().resetNapsCount();
        startNewAlarmOrSetNoActive();
        view.updateNotification(singleAlarmDataBase.getActiveSingleAlarms());
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
        if (singleAlarm.getAlarmDateTime().isSchedule()) {
            AlarmDateTime adt = AlarmDateTimeUpdater.update(singleAlarm.getAlarmDateTime());
            singleAlarm.setAlarmDateTime(adt);
            AlarmManagerManagement.startAlarm(singleAlarm, context);
        } else {
            singleAlarm.setActive(false);
        }
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
    }

}
