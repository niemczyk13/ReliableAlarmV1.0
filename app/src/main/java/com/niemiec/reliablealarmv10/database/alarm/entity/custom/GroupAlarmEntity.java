package com.niemiec.reliablealarmv10.database.alarm.entity.custom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;


@Entity
public class GroupAlarmEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String note;
    public boolean isActive;

    public GroupAlarmEntity() {

    }

    public GroupAlarmEntity(GroupAlarmModel groupAlarmModel) {
        if (groupAlarmModel.getId() != 0) {
            id = groupAlarmModel.getId();
        }
        name = groupAlarmModel.getName();
        note = groupAlarmModel.getNote();
        isActive = groupAlarmModel.isActive();
    }


}
