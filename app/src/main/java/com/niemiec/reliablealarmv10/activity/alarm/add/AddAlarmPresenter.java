package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.os.Bundle;

import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;

public class AddAlarmPresenter extends BasePresenter<AddAlarmContractMVP.View> implements AddAlarmContractMVP.Presenter {

    @Override
    public void downloadAlarm(Bundle bundle) {
        String type = bundle.getString("type");
        if (type.equalsIgnoreCase("create")) {
            view.showAlarm(AlarmDataBase.getDefaultAlarm());
        } else if (type.equalsIgnoreCase("update")) {
            long id = bundle.getLong("id");
            view.showAlarm(AlarmDataBase.getAlarm(id));
        }
    }

    //TODO zapisywanie alarmu
    //pobieramy godzinę i minutę z alarmDateTime
    //i ponownie wprowadzamy do alarmDateTime
    //wtedy ponownie ustali odpowiednią datę
}
