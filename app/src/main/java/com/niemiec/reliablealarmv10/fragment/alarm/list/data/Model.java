package com.niemiec.reliablealarmv10.fragment.alarm.list.data;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Model {
    private final SingleAlarmDataBase singleAlarmDataBase;
    private final GroupAlarmDataBase groupAlarmDataBase;

    public Model(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
        groupAlarmDataBase = GroupAlarmDataBase.getInstance(context);
    }

    public List<SingleAlarmModel> getAllSingleAlarms() {
        List<SingleAlarmModel> singleAlarmEntities = singleAlarmDataBase.getAllSingleAlarms();
        List<SingleAlarmModel> singleAlarmModels = new ArrayList<>();
        for (SingleAlarmModel singleAlarmEntity : singleAlarmEntities) {
            singleAlarmModels.add(new SingleAlarmModel(singleAlarmEntity));
        }
        return singleAlarmModels;
    }

    public List<SingleAlarmEntity> getAllSingleAlarmsEntity() {
        return singleAlarmDataBase.getAllSingleAlarms();
    }

    public SingleAlarmModel getAlarm(long id) {
        return singleAlarmDataBase.getSingleAlarm(id);
    }

    public void updateAlarm(SingleAlarmModel singleAlarm) {
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
    }

    public void deleteAlarms(List<SingleAlarmModel> singleAlarms) {
        for (SingleAlarmModel singleAlarm : singleAlarms) {
            singleAlarmDataBase.deleteSingleAlarm(singleAlarm);
        }
    }

    public List<SingleAlarmModel> getActiveAlarms() {
        return singleAlarmDataBase.getActiveSingleAlarms();
    }

    public GroupAlarmModel getGroupAlarm(long groupAlarmId) {
        return groupAlarmDataBase.getGroupAlarm(groupAlarmId);
    }

    public List<GroupAlarmModel> getGroupAlarms() {
        return groupAlarmDataBase.getAllGroupAlarms();
    }
}
