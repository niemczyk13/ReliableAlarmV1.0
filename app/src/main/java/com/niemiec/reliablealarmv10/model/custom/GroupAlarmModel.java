package com.niemiec.reliablealarmv10.model.custom;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@RequiresApi(api = Build.VERSION_CODES.N)
public class GroupAlarmModel implements Alarm {
    private long id;
    private String name;
    private String note;
    private boolean isActive;
    private List<SingleAlarmEntity> alarms;

    public int compareTimeTo(Alarm alarm) {
        if (alarm instanceof SingleAlarmModel) {

        } else {

        }
        return 0;
    }


    public SingleAlarmModel getEarliestAlarm() {
        return null;
    }
}
