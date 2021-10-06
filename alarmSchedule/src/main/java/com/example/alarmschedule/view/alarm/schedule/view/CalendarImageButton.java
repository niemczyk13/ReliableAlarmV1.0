package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.alarmschedule.R;
import com.example.alarmschedule.view.alarm.schedule.fragment.CalendarDialogFragment;

import java.util.Calendar;

public class CalendarImageButton {
    private final Context context;
    private Calendar alarmDateTime;
    private FragmentManager fragmentManager;
    private LinearLayout calendarButtonLinearLayout;
    private ImageButton calendarButton;
    private CalendarImageButtonClickListener calendarImageButtonClickListener;

    public CalendarImageButton(Context context, int weight) {
        this.context = context;
        createView(weight);
        calendarButton.setOnClickListener(this::onClick);
    }

    private void createView(int weight) {
        createLinerLayout(weight);
        createImageButton();
        addImageButtonToLinearLayout();
    }

    private void createLinerLayout(int weight) {
        calendarButtonLinearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = weight;
        params.topMargin = 0;
        params.bottomMargin = 0;
        calendarButtonLinearLayout.setLayoutParams(params);
    }

    private void createImageButton() {
        calendarButton = new ImageButton(context);
        calendarButton.setImageResource(R.drawable.ic_baseline_calendar_today_24);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        params.bottomMargin = 0;
        calendarButton.setLayoutParams(params);
    }

    private void addImageButtonToLinearLayout() {
        calendarButtonLinearLayout.addView(calendarButton);
    }

    private void onClick(View view) {
        calendarImageButtonClickListener.updateDateTimeBeforeClick();
        showCalendarDialogFragment();
    }

    private void showCalendarDialogFragment() {
        DialogFragment df = new CalendarDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("hour", alarmDateTime.get(Calendar.HOUR_OF_DAY));
        bundle.putInt("minute", alarmDateTime.get(Calendar.MINUTE));
        bundle.putInt("day", alarmDateTime.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("month", alarmDateTime.get(Calendar.MONTH));
        bundle.putInt("year", alarmDateTime.get(Calendar.YEAR));
        df.setArguments(bundle);
        df.show(fragmentManager, "date");
    }

    public View getView() {
        return calendarButtonLinearLayout;
    }

    public void setAlarmDateTime(Calendar alarmDate) {
        this.alarmDateTime = alarmDate;
    }

    public void setFragmentManager(FragmentManager supportFragmentManager) {
        fragmentManager = supportFragmentManager;
    }

    public void addCalendarImageButtonClickListener(CalendarImageButtonClickListener listener) {
        this.calendarImageButtonClickListener = listener;
    }

    public interface CalendarImageButtonClickListener {
        void updateDateTimeBeforeClick();
    }
}
