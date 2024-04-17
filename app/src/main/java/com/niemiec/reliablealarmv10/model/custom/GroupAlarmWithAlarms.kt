package com.niemiec.reliablealarmv10.model.custom

import androidx.room.Embedded
import androidx.room.Relation

data class GroupAlarmWithAlarms(
    @Embedded val groupAlarm: GroupAlarm,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val alarms: List<Alarm>)
