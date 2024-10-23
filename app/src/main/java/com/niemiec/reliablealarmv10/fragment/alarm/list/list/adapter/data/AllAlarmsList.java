package com.niemiec.reliablealarmv10.fragment.alarm.list.list.adapter.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AllAlarmsList {
    private List<Alarm> alarms;
    private List<Boolean> alarmsSelected;

    public AllAlarmsList(List<GroupAlarmModel> groupAlarms, List<SingleAlarmModel> singleAlarms) {
        createAlarmsList(groupAlarms, singleAlarms);
        createSelectedAlarmsList();
    }

    private void createAlarmsList(List<GroupAlarmModel> groupAlarms, List<SingleAlarmModel> singleAlarms) {
        alarms = new ArrayList<>();
        alarms.addAll(groupAlarms);
        alarms.addAll(singleAlarms);

        /*List<Alarm> activeAlarms = alarms.stream().filter(alarm -> alarm.isActive()).sorted(alarm -> {
            if (alarm instanceof SingleAlarmModel) {
                SingleAlarmModel singleAlarmModel = (SingleAlarmModel) alarm;
            } else {

            }
            return -1;
        });*/
    }

    private void createSelectedAlarmsList() {
        alarmsSelected = new ArrayList<>();
        for (int i = 0; i < alarms.size(); i++) {
            alarmsSelected.add(false);
        }
    }
}
