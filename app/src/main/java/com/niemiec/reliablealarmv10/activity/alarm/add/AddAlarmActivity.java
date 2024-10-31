package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;

import com.example.alarmsoundview.view.AlarmSoundView;
import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.BundleNames;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.alarm.manager.AlarmManagerManagement;
import com.niemiec.reliablealarmv10.activity.alarm.manager.notification.AlarmNotificationManager;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;
import com.niemiec.reliablealarmv10.view.nap.NapView;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.SafeAlarmLaunchView;
import com.niemiec.risingview.view.RisingSoundView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddAlarmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AddAlarmContractMVP.View {

    private AddAlarmPresenter presenter;

    private AlarmDateTimeView alarmDateTimeView;
    private AlarmSoundView alarmSoundView;
    private NapView napView;
    private RisingSoundView risingSoundView;
    private SafeAlarmLaunchView safeAlarmLaunchView;
    private SeekBar volumeSeekBar;
    private SwitchMaterial vibrationSwitch;

    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        defineBasicHeaderAppearanceData();
        getViewObjects();
        createAddAlarmPresenter();
        presenter.downloadAlarm(getIntent().getBundleExtra(BundleNames.DATA.name()));
        addOnClickMethodsToButtons();
    }

    private void addOnClickMethodsToButtons() {
        saveButton.setOnClickListener(this::saveButtonClick);
        cancelButton.setOnClickListener(this::cancelButtonClick);
    }

    private void cancelButtonClick(View view) {
        finish();
    }

    private void saveButtonClick(View view) {
        presenter.saveAlarm();
    }

    private SingleAlarmModel createAlarm() {
        SingleAlarmModel singleAlarm = SingleAlarmModel.builder()
                .alarmDateTime(alarmDateTimeView.getAlarmDateTime())
                .sound(alarmSoundView.getSound())
                .nap(napView.getNap())
                .risingSound(risingSoundView.getRisingSound())
                .safeAlarmLaunch(safeAlarmLaunchView.getSafeAlarmLaunch())
                .volume(volumeSeekBar.getProgress())
                .vibration(vibrationSwitch.isChecked())
                .isActive(true)
                .build();

        Bundle bundle = getIntent().getBundleExtra(BundleNames.DATA.name());
        if (bundle.getString(BundleNames.ADD_SINGLE_ALARM_TYPE.name()).equals(AddSingleAlarmType.FOR_GROUP_ALARM.name())) {
            singleAlarm.setGroupAlarmId(bundle.getLong(BundleNames.GROUP_ALARM_ID.name()));
        }

        return singleAlarm;
    }

    private void defineBasicHeaderAppearanceData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }

    private void createAddAlarmPresenter() {
        presenter = new AddAlarmPresenter(this);
        presenter.attach(this);
    }

    private void getViewObjects() {
        alarmDateTimeView = findViewById(R.id.alarm_date_time);
        alarmSoundView = findViewById(R.id.alarm_sound_view);
        napView = findViewById(R.id.nap_view);
        risingSoundView = findViewById(R.id.rising_sound_view);
        safeAlarmLaunchView = findViewById(R.id.safe_alarm_launch_view);
        volumeSeekBar = findViewById(R.id.volume_seek_bar);
        vibrationSwitch = findViewById(R.id.vibration_switch);
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        alarmDateTimeView.setDate(year, month, day);
    }

    @Override
    public void showAlarm(SingleAlarmModel singleAlarm) {
        alarmDateTimeView.initialize(singleAlarm.getAlarmDateTime(), getSupportFragmentManager());
        alarmSoundView.initialize(singleAlarm.getSound());
        napView.initialize(singleAlarm.getNap());
        risingSoundView.initialize(singleAlarm.getRisingSound());
        safeAlarmLaunchView.initialize(singleAlarm.getSafeAlarmLaunch());
        volumeSeekBar.setProgress(singleAlarm.getVolume());
        vibrationSwitch.setChecked(singleAlarm.isVibration());
    }

    @Override
    public SingleAlarmModel getAlarm() {
        alarmDateTimeView.calculateDateToTime();
        return createAlarm();
    }

    @Override
    public void goBackToPreviousActivity() {
        finish();
    }

    @Override
    public void startAlarm(SingleAlarmModel singleAlarm) {
        AlarmManagerManagement.startAlarm(singleAlarm, getApplicationContext());
    }

    @Override
    public void stopAlarm(SingleAlarmModel singleAlarm) {
        AlarmManagerManagement.stopAlarm(singleAlarm, getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void updateNotification(List<SingleAlarmModel> activeSingleAlarms) {
        AlarmNotificationManager.updateNotification(getApplicationContext(), activeSingleAlarms);
    }

}