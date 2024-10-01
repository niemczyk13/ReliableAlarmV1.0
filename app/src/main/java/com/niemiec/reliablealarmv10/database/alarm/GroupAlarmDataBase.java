package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import com.niemiec.reliablealarmv10.database.alarm.model.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.model.custom.SingleAlarmEntity;
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

    //TODO nowy grupowy alarm dodawany jest bez alarmów
    //TODO na początku ma tylko nazwę i notatkę
    public void insertGroupAlarm(GroupAlarmModel groupAlarmModel) {
        GroupAlarmEntity gae = new GroupAlarmEntity(groupAlarmModel);

        long id = dataBaseModel.groupAlarmDAO().insertGroupAlarm(gae);

        List<SingleAlarmEntity> singleAlarmEntities = new ArrayList<>();
        for (SingleAlarmEntity singleAlarm : groupAlarmModel.getAlarms()) {
            singleAlarm.groupAlarmId = id;
            singleAlarmEntities.add(singleAlarm);
        }

        //dataBaseModel.groupAlarmDAO().insertSingleAlarms(singleAlarmEntities);
    }

    public void insertSingleAlarm(SingleAlarmModel singleAlarmModel, long groupAlarmId) {

    }

    public GroupAlarmModel getGroupAlarm(long id) {
        //GroupAlarmEntity groupAlarmEntity = dataBaseModel.groupAlarmDAO().getGroupAlarmById(id);

        //TODO 1. Pobranie GroupAlarmEntity
        //TODO 2. Pobranie listy alarmów dla id group alarm
        //TODO 3. Stworzenie na tej podstawie GroupAlarmModel i zwrócenie
        return null;
    }

    //TODO
    public List<GroupAlarmModel> getAllGroupAlarmsWithoutSingleAlarms() {

        return null;
    }

    public void deleteGroupAlarm(GroupAlarmModel groupAlarmModel) {
        //dataBaseModel.groupAlarmDAO().deleteGroupAlarm(groupAlarmModel);
    }

    public void deleteSingleAlarm(SingleAlarmModel singleAlarmModel) {
        //dataBaseModel.groupAlarmDAO().deleteSingleAlarm(singleAlarmModel);
    }

    //TODO update, jęzeli zmienimy np. włączenie na bazie danych to żeby to się zmieniło
    //TODO dla wszystkich alarmów
    public void updateGroupAlarm(GroupAlarmModel groupAlarmModel) {
        //dataBaseModel.groupAlarmDAO().updateGroupAlarm(groupAlarmModel);
    }

    public void updateSingleAlarm(SingleAlarmModel singleAlarmModel) {
        //dataBaseModel.groupAlarmDAO().updateSingleAlarm(singleAlarmModel);
    }


    public static GroupAlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new GroupAlarmDataBase(context);
        }
        return instance;
    }
}
