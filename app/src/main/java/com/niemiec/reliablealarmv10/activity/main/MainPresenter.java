package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainPresenter extends BasePresenter<MainContractMVP.View> implements MainContractMVP.Presenter {
    private Context context;
    private TypeView typeView;
    private final Model model;

    public MainPresenter(Context context) {
        super();
        model = new Model(context);
        typeView = TypeView.NORMAL;
    }

    @Override
    public void initView() {
        view.showActivity(model.getAllAlarms());
    }

    @Override
    public void onBinButtonClick() {
        view.showAlarmListForDeletion();
        typeView = TypeView.DELETE;
    }

    @Override
    public void onDeleteButtonClick(List<Alarm> alarms) {

    }

    @Override
    public void onCancelButtonClick() {
        view.showNormalView();
        typeView = TypeView.NORMAL;
    }

    @Override
    public void onCreateAlarmButtonClick() {
        view.showCreateNewAlarmActivity();
    }

    @Override
    public void onSwitchOnOffAlarmClick(long id) {
        Alarm alarm = model.getAlarm(id);
        alarm.isActive = !alarm.isActive;
        model.updateAlarm(alarm);
        //TODO to lub animacja w adapterze
        view.updateAlarmList(model.getAllAlarms());
        //TODO URUCHOMIENIE ALARMU!!!!
    }

    @Override
    public void onUpdateAlarmClick() {

    }

    enum TypeView {
        NORMAL, DELETE;
    }
}
