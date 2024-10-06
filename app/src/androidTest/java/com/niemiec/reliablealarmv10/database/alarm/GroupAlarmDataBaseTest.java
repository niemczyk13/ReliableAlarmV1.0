package com.niemiec.reliablealarmv10.database.alarm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.DayOfWeek;
import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.Week;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmWithSingleAlarms;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class GroupAlarmDataBaseTest {
    private Context context;
    private String name = "Work";
    private String note = "Saturday work";

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
    }

    @After
    public void tearDown() {
    }

    private GroupAlarmModel createGroupAlarmModel(String name, String note) {
        return GroupAlarmModel.builder()
                .name(name)
                .note(note)
                .isActive(true)
                .build();
    }

    private List<SingleAlarmEntity> createSingleAlarmEntities(long groupAlarmId, int count) {
        List<SingleAlarmEntity> singleAlarmEntities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            singleAlarmEntities.add(createSingleAlarmEntity(groupAlarmId));
        }
        return singleAlarmEntities;
    }

    private SingleAlarmEntity createSingleAlarmEntity(long groupAlarmId) {
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
    public void insertGroupAlarm() {
        GroupAlarmModel groupAlarm = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        assertNotNull(groupAlarm.getId());
        assertEquals(groupAlarm.getName(), name);
        assertEquals(groupAlarm.getNote(), note);
    }

    @Test
    public void insertSingleAlarm() {
        GroupAlarmModel groupAlarm = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        SingleAlarmEntity singleAlarmMain = createSingleAlarmEntity(groupAlarm.getId());
        SingleAlarmEntity singleAlarmFromDB = GroupAlarmDataBase.getInstance(context).insertSingleAlarm(singleAlarmMain);
        assertNotNull(singleAlarmFromDB.id);
        assertEquals(singleAlarmFromDB.groupAlarmId, groupAlarm.getId());
        assertEquals(singleAlarmFromDB.alarmDateTime.getDateTime().get(Calendar.HOUR), singleAlarmMain.alarmDateTime.getDateTime().get(Calendar.HOUR));
        assertEquals(singleAlarmFromDB.alarmDateTime.getDateTime().get(Calendar.MINUTE), singleAlarmMain.alarmDateTime.getDateTime().get(Calendar.MINUTE));
    }

    @Test
    public void getGroupAlarm() {
        int singleAlarmsCount = 4;
        GroupAlarmModel groupAlarm = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        List<SingleAlarmEntity> singleAlarmEntities = createSingleAlarmEntities(groupAlarm.getId(), singleAlarmsCount);
        groupAlarm.setAlarms(singleAlarmEntities);
        for (SingleAlarmEntity sae : singleAlarmEntities) {
            GroupAlarmDataBase.getInstance(context).insertSingleAlarm(sae);
        }
        GroupAlarmModel groupAlarmFromDB = GroupAlarmDataBase.getInstance(context).getGroupAlarm(groupAlarm.getId());
        assertNotNull(groupAlarmFromDB);
        assertEquals(groupAlarm.getId(), groupAlarmFromDB.getId());
        assertEquals(groupAlarm.getName(), groupAlarmFromDB.getName());
        assertEquals(groupAlarm.getNote(), groupAlarmFromDB.getNote());
        assertEquals(groupAlarm.isActive(), groupAlarmFromDB.isActive());
        assertNotNull(groupAlarmFromDB.getAlarms());
        assertEquals(groupAlarm.getAlarms().size(), groupAlarmFromDB.getAlarms().size());
    }

    @Test
    public void getAllGroupAlarmsWithoutSingleAlarms() {
        int groupAlarmsCount = 10;
        for (int i = 0; i < groupAlarmsCount; i++) {
            GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name + i, note + i));
        }

        List<GroupAlarmModel> groupAlarms = GroupAlarmDataBase.getInstance(context).getAllGroupAlarmsWithoutSingleAlarms();
        assertNotNull(groupAlarms);
        assertEquals(groupAlarms.size(), groupAlarmsCount);
    }

    @Test
    public void getAllSingleAlarmsByGroupAlarmId() {
        int singleAlarmsCount = 5;
        GroupAlarmModel groupAlarm = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        List<SingleAlarmEntity> singleAlarmEntities = createSingleAlarmEntities(groupAlarm.getId(), singleAlarmsCount);
        for (SingleAlarmEntity sae : singleAlarmEntities) {
            GroupAlarmDataBase.getInstance(context).insertSingleAlarm(sae);
        }
        List<SingleAlarmEntity> singleAlarmEntitiesFromDB = GroupAlarmDataBase.getInstance(context).getAllSingleAlarmsByGroupAlarmId(groupAlarm.getId());
        assertEquals(singleAlarmEntitiesFromDB.size(), singleAlarmsCount);
    }

    @Test
    public void updateGroupAlarm() {
        GroupAlarmModel groupAlarmModel = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        String newName = "School";
        String newNote = "2024-2025";
        groupAlarmModel.setName(newName);
        groupAlarmModel.setNote(newNote);
        GroupAlarmDataBase.getInstance(context).updateGroupAlarm(groupAlarmModel);
        GroupAlarmModel updatedGroupAlarm = GroupAlarmDataBase.getInstance(context).getGroupAlarm(groupAlarmModel.getId());
        assertEquals(updatedGroupAlarm.getName(), newName);
        assertEquals(updatedGroupAlarm.getNote(), newNote);
    }

    @Test
    public void deleteGroupAlarm() {
        int singleAlarmsCount = 5;
        GroupAlarmModel groupAlarmModel = GroupAlarmDataBase.getInstance(context).insertGroupAlarm(createGroupAlarmModel(name, note));
        long id = groupAlarmModel.getId();
        List<SingleAlarmEntity> singleAlarmEntities = createSingleAlarmEntities(groupAlarmModel.getId(), singleAlarmsCount);
        groupAlarmModel.setAlarms(singleAlarmEntities);
        for (SingleAlarmEntity sae : singleAlarmEntities) {
            GroupAlarmDataBase.getInstance(context).insertSingleAlarm(sae);
        }

        List<SingleAlarmEntity> singleAlarmEntitiesFromDBBefore = GroupAlarmDataBase.getInstance(context).getAllSingleAlarmsByGroupAlarmId(id);
        assertEquals(singleAlarmEntitiesFromDBBefore.size(), singleAlarmsCount);

        GroupAlarmDataBase.getInstance(context).deleteGroupAlarm(groupAlarmModel);
        GroupAlarmModel deletedGroupAlarm = GroupAlarmDataBase.getInstance(context).getGroupAlarm(id);
        assertNull(deletedGroupAlarm);

        List<SingleAlarmEntity> singleAlarmEntitiesFromDB = GroupAlarmDataBase.getInstance(context).getAllSingleAlarmsByGroupAlarmId(id);
        assertEquals(singleAlarmEntitiesFromDB.size(), 0);
    }
}