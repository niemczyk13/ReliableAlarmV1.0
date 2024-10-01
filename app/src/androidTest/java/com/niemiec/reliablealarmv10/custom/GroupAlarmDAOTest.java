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
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmWithSingleAlarms;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
        String name = "Saturday work";
        String note = "Only on saturday";
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);

        GroupAlarmEntity groupAlarm = groupAlarmDAO.getGroupAlarm(id);
        assertNotNull(id);
        assertEquals(name, groupAlarm.name);
        assertEquals(note, groupAlarm.note);
    }

    GroupAlarmWithSingleAlarms createGroupAlarmWithSingleAlarms(String name, String note, int singleAlarmsCount) {
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        List<SingleAlarmEntity> singleAlarmEntities = createSingleAlarmEntities(groupAlarmEntity.id, singleAlarmsCount);
        GroupAlarmWithSingleAlarms g = new GroupAlarmWithSingleAlarms();
        g.groupAlarmEntity = groupAlarmEntity;
        g.singleAlarms = singleAlarmEntities;
        return g;
    }

    GroupAlarmEntity createGroupAlarmEntity(String name, String note) {
        GroupAlarmEntity groupAlarmEntity = new GroupAlarmEntity();
        groupAlarmEntity.name = name;
        groupAlarmEntity.note = note;

        return groupAlarmEntity;
    }

    List<SingleAlarmEntity> createSingleAlarmEntities(long groupAlarmId, int count) {
        List<SingleAlarmEntity> singleAlarmEntities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            singleAlarmEntities.add(createSingleAlarmEntity(groupAlarmId));
        }
        return singleAlarmEntities;
    }

    SingleAlarmEntity createSingleAlarmEntity(long groupAlarmId) {
        Week week = new Week();
        week.setDay(DayOfWeek.MONDAY, true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2026);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER); // Miesiące są 0-indeksowane (0 = styczeń)
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        calendar.set(Calendar.HOUR_OF_DAY, getRandomHour()); // Godzina w formacie 24-godzinnym
        calendar.set(Calendar.MINUTE, getRandomMinutesOrSeconds());
        calendar.set(Calendar.SECOND, getRandomMinutesOrSeconds());

        SingleAlarmEntity alarm1 = new SingleAlarmEntity();
        alarm1.groupAlarmId = groupAlarmId;
        alarm1.alarmDateTime = new AlarmDateTime(
                Calendar.getInstance(), week
        );
        return alarm1;
    }

    private int getRandomHour() {
        Random random = new Random();
        return random.nextInt(25);
    }

    private int getRandomMinutesOrSeconds() {
        Random random = new Random();
        return random.nextInt(61 );
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