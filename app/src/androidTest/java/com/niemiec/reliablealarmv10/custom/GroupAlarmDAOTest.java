package com.niemiec.reliablealarmv10.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBaseModel;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class GroupAlarmDAOTest {
    private AlarmDataBaseModel alarmDataBaseModel;
    private GroupAlarmDAO groupAlarmDAO;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();

        alarmDataBaseModel = Room.inMemoryDatabaseBuilder(context, AlarmDataBaseModel.class)
                .allowMainThreadQueries()
                .build();
        groupAlarmDAO = alarmDataBaseModel.groupAlarmDAO();
    }

    @After
    public void closeDb() {
        alarmDataBaseModel.close();
    }

    @Test
    public void insertGroupAlarm() {
        GroupAlarmEntity groupAlarmEntity = new GroupAlarmEntity();
        groupAlarmEntity.name = "Szkoła";
        groupAlarmEntity.note = "Test";

        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);
        System.out.println("ID ======= " + id);
        GroupAlarmEntity groupAlarm = groupAlarmDAO.getGroupAlarmById(id);
        assertEquals("Szkoła", groupAlarm.name);
        assertEquals("Test", groupAlarm.note);

        /*List<SingleAlarm> alarms = groupAlarmDAO.getAlarmsByGroupId(groupAlarm.getId());
        assertNotNull(alarms);*/
    }

    SingleAlarmEntity createSingleAlarm() {
        Week week = new Week();
        week.setDay(DayOfWeek.MONDAY, true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER); // Miesiące są 0-indeksowane (0 = styczeń)
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        calendar.set(Calendar.HOUR_OF_DAY, 15); // Godzina w formacie 24-godzinnym
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        SingleAlarmEntity alarm1 = new SingleAlarmEntity();
        alarm1.alarmDateTime = new AlarmDateTime(
                Calendar.getInstance(), week
        );
        return alarm1;
    }

    @Test
    public void getAlarmsByGroupId() {
    }

    @Test
    public void insertSingleAlarm() {
    }

    @Test
    public void getGroupAlarmById() {
    }

    @Test
    public void deleteGroupAlarm() {
    }

    @Test
    public void deleteSingleAlarm() {
    }

    @Test
    public void updateGroupAlarm() {
    }
}