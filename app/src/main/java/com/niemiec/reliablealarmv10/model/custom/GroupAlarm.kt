package com.niemiec.reliablealarmv10.model.custom

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
class GroupAlarm : Alarm {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String = ""
    var note: String = ""
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val singleAlarms: List<SingleAlarm> = emptyList()
    var isActive: Boolean = true
}