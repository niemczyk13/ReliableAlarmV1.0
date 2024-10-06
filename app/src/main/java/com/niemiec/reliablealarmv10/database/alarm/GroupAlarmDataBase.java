package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.List;

public class GroupAlarmDataBase {
    private static GroupAlarmDataBase instance;
    private static AlarmDataBaseModel dataBaseModel;

    private GroupAlarmDataBase(Context context) {
        dataBaseModel = AlarmDataBaseModel.getInstance(context);
    }

    public GroupAlarmModel insertGroupAlarm(GroupAlarmModel groupAlarmModel) {
        GroupAlarmEntity gae = new GroupAlarmEntity(groupAlarmModel);

        long id = dataBaseModel.groupAlarmDAO().insertGroupAlarm(gae);
        groupAlarmModel.setId(id);

        return groupAlarmModel;
    }

    public SingleAlarmEntity insertSingleAlarm(SingleAlarmEntity singleAlarmEntity) {
        long id = dataBaseModel.singleAlarmDAO().insertAlarm(singleAlarmEntity);
        singleAlarmEntity.id = id;
        return singleAlarmEntity;
    }

    public GroupAlarmModel getGroupAlarm(long id) {
        GroupAlarmEntity groupAlarmEntity = dataBaseModel.groupAlarmDAO().getGroupAlarm(id);
        List<SingleAlarmEntity> singleAlarmEntities = dataBaseModel.singleAlarmDAO().getSingleAlarmsByGroupAlarmId(id);
        GroupAlarmModel groupAlarmModel = GroupAlarmModel.builder()
                .id(groupAlarmEntity.id)
                .name(groupAlarmEntity.name)
                .note(groupAlarmEntity.note)
                .isActive(groupAlarmEntity.isActive)
                .alarms(singleAlarmEntities)
                .build();
        return groupAlarmModel;
    }

    //TODO
    public List<GroupAlarmModel> getAllGroupAlarmsWithoutSingleAlarms() {
        List<GroupAlarmEntity> groupAlarmEntities = dataBaseModel.groupAlarmDAO().getAll();
        List<GroupAlarmModel> groupAlarmModels = new ArrayList<>();

        for (GroupAlarmEntity groupAlarmEntity : groupAlarmEntities) {
            GroupAlarmModel groupAlarmModel = GroupAlarmModel.builder()
                    .id(groupAlarmEntity.id)
                    .name(groupAlarmEntity.name)
                    .note(groupAlarmEntity.note)
                    .isActive(groupAlarmEntity.isActive)
                    .build();
            groupAlarmModels.add(groupAlarmModel);
        }
        return groupAlarmModels;
    }

    public List<SingleAlarmEntity> getAllSingleAlarmsByGroupAlarmId(long groupAlarmId) {

        return null;
    }

    //TODO update, jęzeli zmienimy np. włączenie na bazie danych to żeby to się zmieniło
    //TODO dla wszystkich alarmów
    public void updateGroupAlarm(GroupAlarmModel groupAlarmModel) {
        //dataBaseModel.groupAlarmDAO().updateGroupAlarm(groupAlarmModel);


    }

    public void deleteGroupAlarm(GroupAlarmModel groupAlarmModel) {
        //dataBaseModel.groupAlarmDAO().deleteGroupAlarm(groupAlarmModel);
    }

    public static GroupAlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new GroupAlarmDataBase(context);
        }
        return instance;
    }
}
