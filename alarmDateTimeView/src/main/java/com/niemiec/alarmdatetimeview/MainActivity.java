package com.niemiec.alarmdatetimeview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmschedule.view.alarm.schedule.view.AlarmSchedule;
import com.example.customtimepicker.view.CustomTimePicker;
import com.niemiec.alarmdatetimeview.view.AlarmDateTimeView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private CustomTimePicker customTimePicker;
    private AlarmSchedule alarmSchedule;
    private TextView textView;

    private AlarmDateTimeView alarmDateTimeView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //customTimePicker = findViewById(R.id.custom_time_picker);
        //alarmSchedule = findViewById(R.id.alarm_schedule);
        textView = findViewById(R.id.text);

        alarmDateTimeView = findViewById(R.id.alarm_date_time_view);
        alarmDateTimeView.initialize(createTestAlarmDateTime(), getSupportFragmentManager());

        alarmDateTimeView.setOnClickListener(view -> {
            System.out.println(alarmDateTimeView.getAlarmDateTime().getDateTime().getTime());
        });

        /*
        alarmSchedule.initialize(createTestAlarmDateTime(), getSupportFragmentManager());

        customTimePicker.addClockFaceClickListener(() -> {
            alarmSchedule.setTime(customTimePicker.getHour(), customTimePicker.getMinute());
        });

        customTimePicker.addKeyboardInputListener(() -> {
            alarmSchedule.setTime(customTimePicker.getHour(), customTimePicker.getMinute());
        });

         */

    }

    private AlarmDateTime createTestAlarmDateTime() {
        //TODO w ramach testu data na sztywno
        Week week = new Week();
        //week.activeDay(DayOfWeek.MONDAY);
        //week.activeDay(DayOfWeek.SATURDAY);
        week.activeDay(DayOfWeek.THURSDAY);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        //calendar.set(2021, 8, 10, 16,35, 0);
        return new AlarmDateTime(calendar, week);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        alarmSchedule.setDate(i, i1, i2);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void click(View view) {
        textView.setText(alarmDateTimeView.getAlarmDateTime().getDateTime().getTime().toString());
    }
}