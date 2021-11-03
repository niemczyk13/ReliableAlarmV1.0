package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public class MainPresenter extends BasePresenter<MainContractMVP.View> implements MainContractMVP.Presenter {

    public MainPresenter(Context context) {
        super();
        createDataBase(context);
    }

    public void createDataBase(Context context) {
        AlarmDataBase.createDataBase(context);
    }

    //TODO
    @Override
    public List<Alarm> getAllAlarms() {
        return null;
    }

    //TODO
    @Override
    public void removeAlarms(List<Long> ids) {

    }

    //TODO
    @Override
    public void addAlarm() {

    }

    //TODO
    @Override
    public void updateAlarm(long id) {

    }
}
