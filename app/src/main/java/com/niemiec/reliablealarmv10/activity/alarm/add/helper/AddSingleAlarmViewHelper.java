package com.niemiec.reliablealarmv10.activity.alarm.add.helper;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.niemiec.reliablealarmv10.activity.alarm.add.AddSingleAlarmActivity;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AddSingleAlarmViewHelper {
    public final Activity activity;
    private AddSingleAlarmViewManagements viewManagements;

    public AddSingleAlarmViewHelper(AddSingleAlarmActivity activity) {
        this.activity = activity;
        initView();
    }

    private void initView() {
        viewManagements = new AddSingleAlarmViewManagements(activity);
    }


    public void showAlarm(SingleAlarmModel singleAlarm, FragmentManager supportFragmentManager) {
        viewManagements.alarmDateTimeView.initialize(singleAlarm.getAlarmDateTime(), supportFragmentManager);
        viewManagements.alarmSoundView.initialize(singleAlarm.getSound());
        viewManagements.napView.initialize(singleAlarm.getNap());
        viewManagements.risingSoundView.initialize(singleAlarm.getRisingSound());
        viewManagements.safeAlarmLaunchView.initialize(singleAlarm.getSafeAlarmLaunch());
        viewManagements.volumeSeekBar.setProgress(singleAlarm.getVolume());
        viewManagements.vibrationSwitch.setChecked(singleAlarm.isVibration());
    }

    public AddSingleAlarmViewManagements getViewManagement() {
        return viewManagements;
    }

    public void setDate(int year, int month, int day) {
        viewManagements.alarmDateTimeView.setDate(year, month, day);
    }
}
