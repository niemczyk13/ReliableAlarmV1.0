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
    private static boolean dateIsSelect = false;

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
        //TODO ewentualna zmiana roku przy alarmie w sylwestra i nowy rok !!!!!!!!!! - do sprawdzenia
        alarmDateTime.getDateTime().add(Calendar.DAY_OF_YEAR, ONE_WEEK);
    }

    private static Calendar getCalendarInstance() {
        Calendar date = Calendar.getInstance();
        date.setFirstDayOfWeek(Calendar.MONDAY);
        return date;
    }

    private static void calculateOrdinaryDate() {
//        if (alarmIsBeforeNow()) {
//            int date = getCalendarInstance().get(Calendar.DATE);
//            alarmDateTime.getDateTime().set(Calendar.DATE, date);
//            if (alarmIsBeforeNow()) {
//                alarmDateTime.getDateTime().add(Calendar.DATE, ONE_DAY);
//            }
//        }
        if (!dateIsSelect) {
            int date = getCalendarInstance().get(Calendar.DATE);
            if (alarmClockIsBeforeNow()) {
                alarmDateTime.getDateTime().set(Calendar.DATE, date + ONE_DAY);
            } else {
                alarmDateTime.getDateTime().set(Calendar.DATE, date);
            }
        }
    }

    private static boolean alarmClockIsBeforeNow() {
        int alarmHour = alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int alarmMinute = alarmDateTime.getDateTime().get(Calendar.MINUTE);
        Calendar now = getCalendarInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        if (alarmHour < hour) {
            return true;
        } else if (alarmHour == hour && alarmMinute <= minute) {
            return true;
        }
        return false;
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
        setDateIsSelect(year, month, day);

        return alarmDateTime;
    }

    private static void setDateIsSelect(int year, int month, int day) {
        Calendar now = getCalendarInstance();
        if (now.get(Calendar.YEAR) == year && now.get(Calendar.MONTH) == month && now.get(Calendar.DAY_OF_MONTH) == day) {
            dateIsSelect = false;
        } else {
            dateIsSelect = true;
        }
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
