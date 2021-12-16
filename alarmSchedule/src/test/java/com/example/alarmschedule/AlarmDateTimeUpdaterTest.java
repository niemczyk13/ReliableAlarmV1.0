package com.example.alarmschedule;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeUpdater;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class AlarmDateTimeUpdaterTest {
    AlarmDateTime alarmDateTime;

    @Before
    public void createAlarmDateTime() {
        Week week = new Week();
        week.activeDay(DayOfWeek.THURSDAY);
        week.activeDay(DayOfWeek.SUNDAY);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 44);

        alarmDateTime = new AlarmDateTime(calendar, week);
    }

    @Test
    public void updateTest() {
        AlarmDateTime updateADT = AlarmDateTimeUpdater.update(alarmDateTime);

        int update[] = {updateADT.getDateTime().get(Calendar.YEAR),
                        updateADT.getDateTime().get(Calendar.MONTH),
                        updateADT.getDateTime().get(Calendar.DAY_OF_MONTH)};

        int alarm[] = {2021, 11, 19};

        assertArrayEquals(alarm, update);
    }
}
