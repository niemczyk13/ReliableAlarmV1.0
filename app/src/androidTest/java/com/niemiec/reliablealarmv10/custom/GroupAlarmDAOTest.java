package com.niemiec.reliablealarmv10.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBaseModel;
import com.niemiec.reliablealarmv10.database.alarm.custom.GroupAlarmDAO;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GroupAlarmDAOTest {
    private AlarmDataBaseModel alarmDataBaseModel;
    private GroupAlarmDAO groupAlarmDAO;
    private String name = "Saturday work";
    private String note = "Only on saturday";

    @Before
    public void setUp() {
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
    public void insertAndGetGroupAlarm() {
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);

        GroupAlarmEntity groupAlarm = groupAlarmDAO.getGroupAlarm(id);
        assertNotNull(groupAlarm);
        assertEquals(name, groupAlarm.name);
        assertEquals(note, groupAlarm.note);
    }

    @Test
    public void updateGroupAlarm() {
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);

        String newName = "School";
        String newNote = "2024-2025";
        boolean isActive = false;

        GroupAlarmEntity groupAlarmFromDB = groupAlarmDAO.getGroupAlarm(id);
        groupAlarmFromDB.name = newName;
        groupAlarmFromDB.note = newNote;
        groupAlarmFromDB.isActive = isActive;

        groupAlarmDAO.updateGroupAlarm(groupAlarmFromDB);

        GroupAlarmEntity updatedGroupAlarm = groupAlarmDAO.getGroupAlarm(id);

        assertEquals(newName, updatedGroupAlarm.name);
        assertEquals(newNote, updatedGroupAlarm.note);
        assertEquals(isActive, updatedGroupAlarm.isActive);
    }

    @Test
    public void deleteGroupAlarm() {
        GroupAlarmEntity groupAlarmEntity = createGroupAlarmEntity(name, note);
        long id = groupAlarmDAO.insertGroupAlarm(groupAlarmEntity);

        GroupAlarmEntity groupAlarmFromDB = groupAlarmDAO.getGroupAlarm(id);
        groupAlarmDAO.deleteGroupAlarm(groupAlarmFromDB);

        GroupAlarmEntity deletedGroupAlarm = groupAlarmDAO.getGroupAlarm(id);
        assertNull(deletedGroupAlarm);
    }

    private GroupAlarmEntity createGroupAlarmEntity(String name, String note) {
        GroupAlarmEntity groupAlarmEntity = new GroupAlarmEntity();
        groupAlarmEntity.name = name;
        groupAlarmEntity.note = note;
        groupAlarmEntity.isActive = true;

        return groupAlarmEntity;
    }
}