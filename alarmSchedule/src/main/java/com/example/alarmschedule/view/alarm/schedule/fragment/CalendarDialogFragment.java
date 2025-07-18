package com.example.alarmschedule.view.alarm.schedule.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.example.alarmschedule.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CalendarDialogFragment extends DialogFragment {
    private final static long ONE_DAY = 86400000;
    private DatePickerDialog datePickerDialog;
    private Calendar alarmDate;
    private int year, month, day, hour, minute;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getAllArguments();
        createAlarmCalendar();
        createAlarmPickerDialog();
        return datePickerDialog;
    }

    private void getAllArguments() {
        assert getArguments() != null;
        year = getArguments().getInt("year");
        month = getArguments().getInt("month");
        day = getArguments().getInt("day");
        hour = getArguments().getInt("hour");
        minute = getArguments().getInt("minute");
    }

    private void createAlarmCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);
        alarmDate = calendar;
    }

    //@SuppressLint({"ResourceAsColor", "ResourceType"})
    private void createAlarmPickerDialog() {
        datePickerDialog = new DatePickerDialog(requireContext(), R.style.CalendarDialog, (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        datePickerDialog.getDatePicker().setMinDate(getMinDate());
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
    }

    private long getMinDate() {
        long minDate = System.currentTimeMillis();
        if (alarmTimeIsBeforeNow()) {
            minDate += ONE_DAY;
        }
        return minDate;
    }

    private boolean alarmTimeIsBeforeNow() {
        System.out.println("AFTER: " + alarmDate.getTime() + ", NOW: " + Calendar.getInstance().getTime());
        Calendar now = Calendar.getInstance();
        int hourNow = now.get(Calendar.HOUR_OF_DAY);
        int minuteNow = now.get(Calendar.MINUTE);

        int hourAlarm = alarmDate.get(Calendar.HOUR_OF_DAY);
        int minuteAlarm = alarmDate.get(Calendar.MINUTE);

        if (hourAlarm > hourNow) return false;
        else return hourAlarm != hourNow || minuteAlarm <= minuteNow;
    }
}
