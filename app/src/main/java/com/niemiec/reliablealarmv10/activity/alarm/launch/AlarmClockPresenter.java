package com.niemiec.reliablealarmv10.activity.alarm.launch;

import android.content.Context;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private Alarm alarm;

    public AlarmClockPresenter(Context context) {
        super();
        this.context = context;
        alarmDataBase = AlarmDataBase.getInstance(context);
        alarmClockAudioManager = new AlarmClockAudioManager(context);
    }

    public void initView(Long id) {
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
        //TODO
        //2. wibracji
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
    }

    @Override
    public void onNapButtonClick() {
        //TODO
        //1. wywołanie nowego alarmu
        //2. zamknięcie aktywności
        alarmClockAudioManager.stopAlarm();
    }

    @Override
    public void onTurnOffButtonClick() {
        //TODO
        //1. zaktualizowanie alarmu - jeżeli harmonogram to kolejna data i wywołanie alarmu
        // jeżeli pojedyńcza data to aktualizacja isActive na false w bazie danych
        // zamknięcie aktywności
        alarmClockAudioManager.stopAlarm();
    }
}
