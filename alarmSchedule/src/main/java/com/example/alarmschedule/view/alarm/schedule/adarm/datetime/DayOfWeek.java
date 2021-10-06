package com.example.alarmschedule.view.alarm.schedule.adarm.datetime;

import java.util.Calendar;

public enum DayOfWeek {
    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY),
    SATURDAY(Calendar.SATURDAY),
    SUNDAY(Calendar.SUNDAY);

    private final int calendarValue;

    DayOfWeek(int calendarValue) {
        this.calendarValue = calendarValue;
    }

    public int getValue() {
        return calendarValue;
    }
}
