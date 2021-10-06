package com.example.alarmschedule.view.alarm.schedule.logic;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;

import java.util.Calendar;
import java.util.List;

public class AlarmDateTimeUpdater {
    private static final int ONE_DAY = 1;
    private static final int ONE_WEEK = 7;
    private static AlarmDateTime alarmDateTime;

    public static AlarmDateTime update(AlarmDateTime adt) {
        alarmDateTime = adt;

        if (alarmDateTime.isSchedule()) {
            calculateDateForSchedule();
        } else {
            calculateOrdinaryDate();
        }
        return alarmDateTime;
    }

    private static void calculateDateForSchedule() {
        List<DayOfWeek> days = alarmDateTime.getWeek().getOnlySelectedDays();

        for (DayOfWeek day : days) {
            Calendar date = getCalendarInstance();
            date.set(Calendar.DAY_OF_WEEK, day.getValue());
            Calendar now = getCalendarInstance();
            alarmDateTime.getDateTime().set(Calendar.YEAR, now.get(Calendar.YEAR));
            alarmDateTime.getDateTime().set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR));
            alarmDateTime.getDateTime().set(Calendar.DAY_OF_WEEK, day.getValue());
            if (alarmDateTime.getDateTime().after(getCalendarInstance())) {
                return;
            }
        }
        Calendar now = getCalendarInstance();
        alarmDateTime.getDateTime().set(Calendar.YEAR, now.get(Calendar.YEAR));
        alarmDateTime.getDateTime().set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR));
        alarmDateTime.getDateTime().set(Calendar.DAY_OF_WEEK, days.get(0).getValue());
        alarmDateTime.getDateTime().add(Calendar.DAY_OF_YEAR, ONE_WEEK);
    }

    private static Calendar getCalendarInstance() {
        Calendar date = Calendar.getInstance();
        date.setFirstDayOfWeek(Calendar.MONDAY);
        return date;
    }

    private static void calculateOrdinaryDate() {
        //TODO dodać, że jeżeli mniej niż 1 dzień różnicy to nie dodawać
        if (alarmIsBeforeNow()) {
            int date = getCalendarInstance().get(Calendar.DATE);
            alarmDateTime.getDateTime().set(Calendar.DATE, date);
            if (alarmIsBeforeNow()) {
                alarmDateTime.getDateTime().add(Calendar.DATE, ONE_DAY);
            }
        }
    }

    private static boolean alarmIsBeforeNow() {
        return !alarmDateTime.getDateTime().after(getCalendarInstance());
    }

    public static AlarmDateTime updateTime(int hour, int minute) {
        alarmDateTime.getDateTime().set(Calendar.HOUR_OF_DAY, hour);
        alarmDateTime.getDateTime().set(Calendar.MINUTE, minute);
        alarmDateTime = update(alarmDateTime);
        return alarmDateTime;
    }

    public static AlarmDateTime updateDate(int year, int month, int day) {
        alarmDateTime.getDateTime().set(Calendar.YEAR, year);
        alarmDateTime.getDateTime().set(Calendar.MONTH, month);
        alarmDateTime.getDateTime().set(Calendar.DAY_OF_MONTH, day);
        alarmDateTime.getWeek().clear();

        return alarmDateTime;
    }

    public static AlarmDateTime getAlarmDateTime() {
        return alarmDateTime;
    }

    public static AlarmDateTime updateWeek(Week week) {
        alarmDateTime.setWeek(week);
        if (week.isSchedule()) {
            calculateDateForSchedule();
        } else {
            updateForAllDaysUncheck();
        }
        return alarmDateTime;
    }

    public static AlarmDateTime updateForAllDaysUncheck() {
        alarmDateTime.getWeek().clear();
        Calendar date = getCalendarInstance();
        alarmDateTime.getDateTime().set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        calculateOrdinaryDate();
        return alarmDateTime;
    }

    public static AlarmDateTime getFinalVersionAlarmDateTime() {
        alarmDateTime = update(alarmDateTime);
        return alarmDateTime;
    }
}
