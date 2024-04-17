package com.niemiec.reliablealarmv10.model.custom;

import static org.junit.Assert.*;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class SingleAlarmTest {
    private SingleAlarm firstAlarm;
    private SingleAlarm secondAlarm;

    @Before
    public void setUp() throws Exception {
        firstAlarm = createFirstAlarm();
        secondAlarm = createSecondAlarm(firstAlarm);
    }

    private SingleAlarm createSecondAlarm(SingleAlarm firstAlarm) {
        Date date = new Date();
        date.setTime(firstAlarm.alarmDateTime.getDateTime().getTimeInMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        AlarmDateTime adt = new AlarmDateTime(cal, new Week());
        SingleAlarm alarm = new SingleAlarm();
        alarm.alarmDateTime = adt;
        return alarm;
    }

    private SingleAlarm createFirstAlarm() {
        AlarmDateTime adt = new AlarmDateTime(Calendar.getInstance(), new Week());
        SingleAlarm alarm = new SingleAlarm();
        alarm.alarmDateTime = adt;
        return alarm;
    }

    @Test
    public void compareTimeTo() {
        assertEquals(0, firstAlarm.compareTimeTo(secondAlarm));
        addOneMinute(secondAlarm);
        assertEquals(-1, firstAlarm.compareTimeTo(secondAlarm));
        subtractOneMinute(secondAlarm);
        assertEquals(0, firstAlarm.compareTimeTo(secondAlarm));

        addOneMinute(firstAlarm);
        assertEquals(1, firstAlarm.compareTimeTo(secondAlarm));
        addOneMinute(secondAlarm);
        assertEquals(0, firstAlarm.compareTimeTo(secondAlarm));

        addOneHour(secondAlarm);
        assertEquals(-1, firstAlarm.compareTimeTo(secondAlarm));
        addOneHour(firstAlarm);
        assertEquals(0, firstAlarm.compareTimeTo(secondAlarm));
        addOneHour(firstAlarm);
        assertEquals(1, firstAlarm.compareTimeTo(secondAlarm));
        addOneMinute(secondAlarm);
        assertEquals(1, firstAlarm.compareTimeTo(secondAlarm));

        subtractOneHour(firstAlarm);
        assertEquals(-1, firstAlarm.compareTimeTo(secondAlarm));
    }

    @Test
    public void compareDateTimeTo() {
        firstAlarm = createFirstAlarm();
        secondAlarm = createSecondAlarm(firstAlarm);

        assertEquals(0, firstAlarm.compareDateTimeTo(secondAlarm));
        addOneDay(secondAlarm);
        assertEquals(-1, firstAlarm.compareDateTimeTo(secondAlarm));
        addOneDay(secondAlarm);
        assertEquals(-1, firstAlarm.compareDateTimeTo(secondAlarm));
        addOneDay(firstAlarm);
        assertEquals(-1, firstAlarm.compareDateTimeTo(secondAlarm));
        subtractOneDay(secondAlarm);
        assertEquals(0, firstAlarm.compareDateTimeTo(secondAlarm));
        addOneDay(firstAlarm);
        assertEquals(1, firstAlarm.compareDateTimeTo(secondAlarm));
    }

    public void addOneMinute(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, 1);
    }

    public void addOneHour(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.HOUR, 1);
    }

    public void subtractOneMinute(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.MINUTE, -1);
    }

    public void subtractOneHour(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.HOUR, -1);
    }

    public void addOneDay(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.DAY_OF_YEAR, 1);
    }

    public void subtractOneDay(SingleAlarm alarm) {
        alarm.alarmDateTime.getDateTime().add(Calendar.DAY_OF_YEAR, -1);
    }
}