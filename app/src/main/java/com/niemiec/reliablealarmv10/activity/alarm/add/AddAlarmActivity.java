package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmsoundview.model.Sound;
import com.example.alarmsoundview.view.AlarmSoundView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.nap.NapView;
import com.niemiec.reliablealarmv10.view.nap.model.Nap;
import com.niemiec.risingview.model.RisingSound;
import com.niemiec.risingview.view.RisingSoundValue;
import com.niemiec.risingview.view.RisingSoundView;

import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddAlarmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.BLACK);

        getViewObjects();


        //TODO - pobieram dane z bazy w presenter
        Alarm alarm = AlarmDataBase.getDefaultAlarm();
        alarmDateTimeView.initialize(alarm.alarmDateTime, getSupportFragmentManager());
        //TODO - przekazuje do metody showEditAlarm(Alarm)
        volumeSeekBar.setMax(100);
        volumeSeekBar.setProgress(alarm.volume);
        //alarmDateTimeView.initialize(createTestAlarmDateTime(), getSupportFragmentManager());

        Sound sound = new Sound();
        sound.setSoundId(com.example.alarmsoundview.R.raw.creep);
        sound.setSoundName("Piosenka");
        sound.setUri("uri/uri");
        sound.setPersonal(false);
        alarmSoundView.initialize(sound);

        RisingSoundView view = findViewById(com.niemiec.risingview.R.id.rising_sound_view);

        RisingSound risingSound = new RisingSound();
        risingSound.setRisingSoundTime(RisingSoundValue.SECOND.getValue());
        view.initialize(risingSound);

        NapView napView = findViewById(R.id.nap_view);
        Nap nap = new Nap();
        nap.setNapTime(2);
        napView.initialize(nap);

    }

    private void getViewObjects() {
        alarmDateTimeView = findViewById(R.id.alarm_date_time_view);
        alarmSoundView = findViewById(R.id.alarm_sound_view);
        napView = findViewById(R.id.nap_view);
        risingSoundView = findViewById(R.id.rising_sound_view);
        volumeSeekBar = findViewById(R.id.volume_seek_bar);
        vibrationSwitch = findViewById(R.id.vibration_switch);
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);
    }

    private AlarmDateTime createTestAlarmDateTime() {
        //TODO w ramach testu data na sztywno
        Week week = new Week();
        //week.activeDay(DayOfWeek.MONDAY);
        //week.activeDay(DayOfWeek.SATURDAY);
        week.activeDay(DayOfWeek.THURSDAY);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 8, 10, 16,35, 0);
        return new AlarmDateTime(calendar, week);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        alarmDateTimeView.setDate(year, month, day);
    }
}