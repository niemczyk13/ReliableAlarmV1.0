package com.niemiec.reliablealarmv10.activity.alarm.launch;

import android.content.Context;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

public class AlarmClockPresenter extends BasePresenter<AlarmClockContractMVP.View> implements AlarmClockContractMVP.Presenter {
    private AlarmDataBase alarmDataBase;

    public AlarmClockPresenter(Context context) {
        super();
        alarmDataBase = AlarmDataBase.getInstance(context);
    }

    public void initView(Long id) {
        Alarm alarm = alarmDataBase.getAlarm(id);
        if (alarm.nap.isActive()) {
            view.showAlarmClockWithNap();
        } else {
            view.showAlarmClockWithoutNap();
        }
    }
}
