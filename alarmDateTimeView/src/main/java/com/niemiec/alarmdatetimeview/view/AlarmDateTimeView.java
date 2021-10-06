package com.niemiec.alarmdatetimeview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.view.AlarmSchedule;
import com.example.customtimepicker.view.CustomTimePicker;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AlarmDateTimeView extends LinearLayout {
    private CustomTimePicker customTimePicker;
    private AlarmSchedule alarmSchedule;

    public AlarmDateTimeView(Context context) {
        super(context);
        setProperties(context);
    }

    public AlarmDateTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProperties(context);
    }

    public AlarmDateTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties(context);
    }

    private void setProperties(Context context) {
        createViews(context);
        setPropertiesToMainView();
        addViews();
        addListeners();
    }

    private void createViews(Context context) {
        customTimePicker = new CustomTimePicker(context);
        alarmSchedule = new AlarmSchedule(context);
    }

    private void setPropertiesToMainView() {
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.setLayoutParams(params);
    }

    private void addViews() {
        super.addView(customTimePicker);
        super.addView(alarmSchedule);
    }

    private void addListeners() {
        customTimePicker.addClockFaceClickListener(() -> {
            alarmSchedule.setTime(customTimePicker.getHour(), customTimePicker.getMinute());
        });

        customTimePicker.addKeyboardInputListener(() -> {
            alarmSchedule.setTime(customTimePicker.getHour(), customTimePicker.getMinute());
        });
    }

    public AlarmDateTime getAlarmDateTime() {
        return alarmSchedule.getAlarmDateTime();
    }

    public void initialize(AlarmDateTime alarmDateTime, FragmentManager fragmentManager) {
        customTimePicker.viewHour(alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY));
        customTimePicker.viewMinute(alarmDateTime.getDateTime().get(Calendar.MINUTE));
        alarmSchedule.initialize(alarmDateTime, fragmentManager);
    }

    public void setDate(int year, int month, int day) {
        alarmSchedule.setDate(year, month, day);
    }
}
