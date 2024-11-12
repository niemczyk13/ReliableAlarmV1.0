package com.niemiec.reliablealarmv10.custom;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBaseModel;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.custom.SingleAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class SingleAlarmDAOTest {
    private AlarmDataBaseModel alarmDataBaseModel;
    private GroupAlarmDAO groupAlarmDAO;
    private SingleAlarmDAO singleAlarmDAO;
    private String name = "Saturday work";
    private String note = "Only on saturday";

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();

        alarmDataBaseModel = Room.inMemoryDatabaseBuilder(context, AlarmDataBaseModel.class)
                .allowMainThreadQueries()
                .build();
        groupAlarmDAO = alarmDataBaseModel.groupAlarmDAO();
        singleAlarmDAO = alarmDataBaseModel.singleAlarmDAO();
    }

    @After
    public void closeDb() {
        alarmDataBaseModel.close();
    }

    @Test
    public void getAllSingleAlarmsWithoutGroupId() {
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);

        for (int i = 0; i < 5; i++) {
            SingleAlarmModel singleAlarmModel = createSingleAlarmEntity(id);
            singleAlarmDAO.insertAlarm(new SingleAlarmEntity(singleAlarmModel));
        }

        for (int i = 0; i < 5; i++) {
            SingleAlarmModel singleAlarmModel = createSingleAlarmEntity(0);
            singleAlarmDAO.insertAlarm(new SingleAlarmEntity(singleAlarmModel));
        }

        List<SingleAlarmEntity> singleAlarmEntities = singleAlarmDAO.getAllSingleAlarmsWithoutGroupId();
        assertEquals(5, singleAlarmEntities.size());

        List<SingleAlarmEntity> allSingleAlarms = singleAlarmDAO.getAll();
        assertEquals(10, allSingleAlarms.size());
    }

    private GroupAlarmEntity createGroupAlarmEntity(String name, String note) {
        GroupAlarmEntity groupAlarmEntity = new GroupAlarmEntity();
        groupAlarmEntity.name = name;
        groupAlarmEntity.note = note;
        groupAlarmEntity.isActive = true;

        return groupAlarmEntity;
    }

    private SingleAlarmModel createSingleAlarmEntity(long groupAlarmId) {
        Week week = new Week();
        week.setDay(DayOfWeek.MONDAY, true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2026);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER); // Miesiące są 0-indeksowane (0 = styczeń)
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        calendar.set(Calendar.HOUR_OF_DAY, getRandomHour()); // Godzina w formacie 24-godzinnym
        calendar.set(Calendar.MINUTE, getRandomMinutesOrSeconds());
        calendar.set(Calendar.SECOND, getRandomMinutesOrSeconds());

        SingleAlarmModel alarm1 = new SingleAlarmModel();
        if (groupAlarmId > 0)
            alarm1.setGroupAlarmId(groupAlarmId);
        alarm1.setAlarmDateTime(new AlarmDateTime(Calendar.getInstance(), week));
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
}