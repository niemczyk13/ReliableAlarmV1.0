package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {

    private Type type;
    private long id;

    @Override
    public void downloadAlarm(Bundle bundle) {
        type = (Type) bundle.getSerializable("type");
        if (type == Type.CREATE) {
            id = 0;
            view.showAlarm(AlarmDataBase.getDefaultAlarm());
        } else if (type == Type.UPDATE) {
            id = bundle.getLong("id");
            view.showAlarm(AlarmDataBase.getAlarm(id));
        }
    }

    @Override
    public void saveAlarm(Alarm alarm) {
        view.updateAlarmDate();
        if (type == Type.CREATE) {
            AlarmDataBase.insertAlarm(alarm);
        } else if (type == Type.UPDATE) {
            alarm.id = id;
            AlarmDataBase.updateAlarm(alarm);
        }
        view.goBackToPreviousActivity();
        //TODO w view.powrót do czweśniejszej aktywności
    }

    public enum Type {
        CREATE, UPDATE;
    }
}
