package com.niemiec.reliablealarmv10.activity.alarm.launch;

import android.content.Context;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private AlarmDataBase alarmDataBase;
    private Alarm alarm;

    public AlarmClockPresenter(Context context) {
        super();
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    public void initView(Long id) {
        alarm = alarmDataBase.getAlarm(id);
        if (alarm.nap.isActive()) {
            view.showAlarmClockWithNap();
        } else {
            view.showAlarmClockWithoutNap();
        }
        callUpAlarm();
    }

    private void callUpAlarm() {
        //TODO
        //1. wywołanie dźwięku (jeżeli nie działa ścieżka to dźwięk domyślny), jeżeli narastający dźwięk to inaczej, głośność
        //2. wibracji

    }

    @Override
    public void onNapButtonClick() {
        //TODO
        //1. wywołanie nowego alarmu
        //2. zamknięcie aktywności
    }

    @Override
    public void onTurnOffButtonClick() {
        //TODO
        //1. zaktualizowanie alarmu - jeżeli harmonogram to kolejna data i wywołanie alarmu
        // jeżeli pojedyńcza data to aktualizacja isActive na false w bazie danych
        // zamknięcie aktywności
    }
}
