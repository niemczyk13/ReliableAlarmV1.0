package com.niemiec.reliablealarmv10.activity.alarm.add.helper;

import android.app.Activity;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.alarmsoundview.view.AlarmSoundView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.view.nap.NapView;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchView;
import com.niemiec.risingview.view.RisingSoundView;

public class AddSingleAlarmViewManagements {
    public AlarmDateTimeView alarmDateTimeView;
    public AlarmSoundView alarmSoundView;
    public NapView napView;
    public RisingSoundView risingSoundView;
    public SafeAlarmLaunchView safeAlarmLaunchView;
    public SeekBar volumeSeekBar;
    public SwitchMaterial vibrationSwitch;
    public Button cancelButton;
    public Button saveButton;

    public AddSingleAlarmViewManagements(Activity activity) {
        initView(activity);
    }

    private void initView(Activity activity) {
        alarmDateTimeView = activity.findViewById(R.id.alarm_date_time);
        alarmSoundView = activity.findViewById(R.id.alarm_sound_view);
        napView = activity.findViewById(R.id.nap_view);
        risingSoundView = activity.findViewById(R.id.rising_sound_view);
        safeAlarmLaunchView = activity.findViewById(R.id.safe_alarm_launch_view);
        volumeSeekBar = activity.findViewById(R.id.volume_seek_bar);
        vibrationSwitch = activity.findViewById(R.id.vibration_switch);
        cancelButton = activity.findViewById(R.id.cancel_button);
        saveButton = activity.findViewById(R.id.save_button);
    }

}
