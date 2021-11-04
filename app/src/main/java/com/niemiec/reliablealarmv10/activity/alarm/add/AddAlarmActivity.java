package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;

import com.example.alarmsoundview.view.AlarmSoundView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.nap.NapView;
import com.niemiec.risingview.view.RisingSoundView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AddAlarmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AddAlarmContractMVP.View {

    private AddAlarmPresenter presenter;

    private AlarmDateTimeView alarmDateTimeView;
    private AlarmSoundView alarmSoundView;
    private NapView napView;
    private RisingSoundView risingSoundView;
    private SeekBar volumeSeekBar;
    private SwitchMaterial vibrationSwitch;

    private Button cancelButton;
    private Button saveButton;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        defineBasicHeaderAppearanceData();
        getViewObjects();
        createAddAlarmPresenter();
        presenter.downloadAlarm(getIntent().getBundleExtra("data"));
    }

    private void defineBasicHeaderAppearanceData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);
    }

    private void createAddAlarmPresenter() {
        presenter = new AddAlarmPresenter();
        presenter.attach(this);
    }

    private void getViewObjects() {
        alarmDateTimeView = findViewById(R.id.alarm_date_time);
        alarmSoundView = findViewById(R.id.alarm_sound_view);
        napView = findViewById(R.id.nap_view);
        risingSoundView = findViewById(R.id.rising_sound_view);
        volumeSeekBar = findViewById(R.id.volume_seek_bar);
        vibrationSwitch = findViewById(R.id.vibration_switch);
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        alarmDateTimeView.setDate(year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void showAlarm(Alarm alarm) {
        alarmDateTimeView.initialize(alarm.alarmDateTime, getSupportFragmentManager());
        alarmSoundView.initialize(alarm.sound);
        napView.initialize(alarm.nap);
        risingSoundView.initialize(alarm.risingSound);
        volumeSeekBar.setProgress(alarm.volume);
        vibrationSwitch.setChecked(alarm.vibration);
    }
}