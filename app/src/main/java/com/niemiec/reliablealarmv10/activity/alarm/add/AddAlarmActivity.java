package com.niemiec.reliablealarmv10.activity.alarm.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmsoundview.model.Sound;
import com.example.alarmsoundview.view.AlarmSoundView;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;
import com.niemiec.reliablealarmv10.R;
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

        alarmDateTimeView = findViewById(R.id.alarm_date_time);
        alarmDateTimeView.initialize(createTestAlarmDateTime(), getSupportFragmentManager());

        alarmSoundView = findViewById(R.id.alarm_sound_view);
        Sound sound = new Sound();
        sound.setId(com.example.alarmsoundview.R.raw.creep);
        sound.setName("Piosenka");
        sound.setUri("uri/uri");
        sound.setPersonal(false);
        alarmSoundView.initialize(sound);

        RisingSoundView view = findViewById(com.niemiec.risingview.R.id.rising_sound_view);

        RisingSound risingSound = new RisingSound();
        risingSound.setTime(RisingSoundValue.SECOND.getValue());
        view.initialize(risingSound);

        NapView napView = findViewById(R.id.nap_view);
        Nap nap = new Nap();
        nap.setTime(2);
        napView.initialize(nap);

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