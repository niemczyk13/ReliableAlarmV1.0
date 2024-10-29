package com.niemiec.reliablealarmv10.fragment.alarm.list.data;

import android.content.Context;
import android.os.Build;

import com.niemiec.reliablealarmv10.database.alarm.GroupAlarmDataBase;
import com.niemiec.reliablealarmv10.database.alarm.SingleAlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class Model {
    private final SingleAlarmDataBase singleAlarmDataBase;
    private final GroupAlarmDataBase groupAlarmDataBase;

    public Model(Context context) {
        singleAlarmDataBase = SingleAlarmDataBase.getInstance(context);
        groupAlarmDataBase = GroupAlarmDataBase.getInstance(context);
    }

    public List<SingleAlarmModel> getAllSingleAlarms() {
        return singleAlarmDataBase.getAllSingleAlarms();
    }

    public List<SingleAlarmModel> getAllSingleAlarmsEntity() {
        return singleAlarmDataBase.getAllSingleAlarms();
    }

    public SingleAlarmModel getAlarm(long id) {
        return singleAlarmDataBase.getSingleAlarm(id);
    }

    public void updateAlarm(SingleAlarmModel singleAlarm) {
        singleAlarmDataBase.updateSingleAlarm(singleAlarm);
    }

    public void updateAlarm(GroupAlarmModel groupAlarm) {
        groupAlarmDataBase.updateGroupAlarm(groupAlarm);
    }

    public void deleteAlarms(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            if (alarm instanceof SingleAlarmModel singleAlarm)
                singleAlarmDataBase.deleteSingleAlarm(singleAlarm);
            else if (alarm instanceof GroupAlarmModel groupAlarm)
                groupAlarmDataBase.deleteGroupAlarm(groupAlarm);
        }
    }

    public List<SingleAlarmModel> getActiveSingleAlarms() {
        return singleAlarmDataBase.getActiveSingleAlarms().stream()
                .filter(this::singleAndGroupAlarmAreActive).collect(Collectors.toList());
    }

    private boolean singleAndGroupAlarmAreActive(SingleAlarmModel singleAlarm) {
        if (singleAlarm.isInGroupAlarm()) {
            GroupAlarmModel groupAlarmModel = getGroupAlarm(singleAlarm.getGroupAlarmId());
            return singleAlarm.isActive() && groupAlarmModel.isActive();
        }
        return singleAlarm.isActive();
    }

    public GroupAlarmModel getGroupAlarm(long groupAlarmId) {
        return groupAlarmDataBase.getGroupAlarm(groupAlarmId);
    }

    public List<GroupAlarmModel> getGroupAlarms() {
        return groupAlarmDataBase.getAllGroupAlarms();
    }

    public List<SingleAlarmModel> getAllSingleAlarmsWithoutGroupId() {
        return singleAlarmDataBase.getAllSingleAlarmsWithoutGroupId();
    }
}
