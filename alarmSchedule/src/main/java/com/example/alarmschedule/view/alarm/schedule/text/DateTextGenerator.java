package com.example.alarmschedule.view.alarm.schedule.text;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;

import java.util.Calendar;

public class DateTextGenerator {
    public static String generate(Week weekSchedule) {
        return DescriptionOfRepeatingDays.getDescription(weekSchedule.getDaysWhenMondayIsFirst());
    }

    public static String generate(Calendar date) {
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        return DescriptionOfSelectedDay.getDescription(day, month, dayOfWeek);
    }
}
