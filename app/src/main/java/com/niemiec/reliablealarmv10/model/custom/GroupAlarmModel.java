package com.niemiec.reliablealarmv10.model.custom;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.GroupAlarmEntity;
import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupAlarmModel {
    private long id;
    private String name;
    private String note;
    private boolean isActive;
    private List<SingleAlarmEntity> alarms;
}
