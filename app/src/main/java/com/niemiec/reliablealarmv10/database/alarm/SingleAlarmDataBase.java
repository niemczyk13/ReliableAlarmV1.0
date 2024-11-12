package com.niemiec.reliablealarmv10.database.alarm;

import android.content.Context;

import androidx.annotation.NonNull;

import com.niemiec.reliablealarmv10.database.alarm.entity.basic.BasicAlarm;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SingleAlarmDataBase {
    private static SingleAlarmDataBase instance;
    private static GroupAlarmDataBase groupAlarmDataBase;
    private static AlarmDataBaseModel dataBaseModel;

    private SingleAlarmDataBase(Context context) {
        createDataBase(context);
    }

    private static void createDataBase(Context context) {
        dataBaseModel = AlarmDataBaseModel.getInstance(context);
        groupAlarmDataBase = GroupAlarmDataBase.getInstance(context);
        createBasicAlarm();
    }

    private static void createBasicAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        if (basicAlarm == null)
            dataBaseModel.basicAlarmDAO().insertBasicAlarm(new BasicAlarm());
    }

    public void insertSingleAlarm(SingleAlarmModel singleAlarm) {
        SingleAlarmEntity singleAlarmEntity = new SingleAlarmEntity(singleAlarm);
        dataBaseModel.singleAlarmDAO().insertAlarm(singleAlarmEntity);
    }

    public SingleAlarmModel getDefaultSingleAlarm() {
        BasicAlarm basicAlarm = dataBaseModel.basicAlarmDAO().getBasicAlarm();
        return new SingleAlarmModel(basicAlarm.getAlarm());
    }

    public SingleAlarmModel getSingleAlarm(long id) {
        return new SingleAlarmModel(dataBaseModel.singleAlarmDAO().getAlarm(id));
    }

    public void updateSingleAlarm(SingleAlarmModel singleAlarm) {
        dataBaseModel.singleAlarmDAO().updateAlarm(new SingleAlarmEntity(singleAlarm));
    }

    public List<SingleAlarmModel> getAllSingleAlarms() {
        return getSingleAlarmModels(dataBaseModel.singleAlarmDAO().getAll());
    }

    public List<SingleAlarmModel> getSingleAlarmsBefore(Calendar date) {
        return getSingleAlarmModels(dataBaseModel.singleAlarmDAO().getAlarmsBefore(date.getTimeInMillis()));
    }

    public static SingleAlarmDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new SingleAlarmDataBase(context);
        }
        return instance;
    }

    public void deleteSingleAlarm(SingleAlarmModel singleAlarm) {
        dataBaseModel.singleAlarmDAO().deleteAlarm(new SingleAlarmEntity(singleAlarm));
    }

    public SingleAlarmModel getLastSingleAlarm() {return new SingleAlarmModel(dataBaseModel.singleAlarmDAO().getLastAlarm());}

    public List<SingleAlarmModel> getActiveSingleAlarms() {
        return getSingleAlarmModels(dataBaseModel.singleAlarmDAO().getActiveAlarms());
    }

    public List<SingleAlarmModel> getActiveSingleAlarmsWithGroupAlarmActiveIncluded() {
        List<SingleAlarmModel> singleAlarmModels = new ArrayList<>();

        for (SingleAlarmModel singleAlarmModel : getSingleAlarmModels(dataBaseModel.singleAlarmDAO().getActiveAlarms())) {
            if (singleAlarmModel.isInGroupAlarm()) {
                GroupAlarmModel groupAlarmModel = groupAlarmDataBase.getGroupAlarm(singleAlarmModel.getGroupAlarmId());
                if (groupAlarmModel.isActive()) {
                    singleAlarmModels.add(singleAlarmModel);
                }
            } else {
                singleAlarmModels.add(singleAlarmModel);
            }
        }
        return singleAlarmModels;
    }

    private static @NonNull List<SingleAlarmModel> getSingleAlarmModels(List<SingleAlarmEntity> singleAlarmEntities) {
        List<SingleAlarmModel> singleAlarmModels = new ArrayList<>();
        for (SingleAlarmEntity singleAlarmEntity : singleAlarmEntities) {
            singleAlarmModels.add(new SingleAlarmModel(singleAlarmEntity));
        }
        return singleAlarmModels;
    }

    public List<SingleAlarmModel> getAllSingleAlarmsWithoutGroupId() {
        return getSingleAlarmModels(dataBaseModel.singleAlarmDAO().getAllSingleAlarmsWithoutGroupId());
    }
}
