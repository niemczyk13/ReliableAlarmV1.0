package com.niemiec.reliablealarmv10.database.alarm.model.custom;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupAlarmWithSingleAlarms {
    @Embedded
    public GroupAlarmEntity groupAlarmEntity;

    @Relation(parentColumn = "id", entityColumn = "groupAlarmId")
    public List<SingleAlarmEntity> singleAlarms;
}
