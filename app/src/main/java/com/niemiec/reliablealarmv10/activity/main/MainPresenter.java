package com.niemiec.reliablealarmv10.activity.main;

import android.content.Context;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

public class MainPresenter extends BasePresenter<MainContractMVP.View> implements MainContractMVP.Presenter {
    private Model model;

    public MainPresenter(Context context) {
        super();
        model = new Model(context);
    }


    @Override
    public void initView() {
        //TODO wywołanie showMainList(List<Alarm>) -> to idzie do adaptera
    }

    @Override
    public void onBinButtonClick() {

    }

    @Override
    public void onDeleteButtonClick(List<Alarm> alarms) {

    }

    @Override
    public void onCancelButtonClick() {

    }

    @Override
    public void onCreateAlarmButtonClick() {

    }

    @Override
    public void onSwitchOnOffAlarmClick() {

    }

    @Override
    public void onUpdateAlarmClick() {

    }

}
