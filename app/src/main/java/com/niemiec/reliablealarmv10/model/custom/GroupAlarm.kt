package com.niemiec.reliablealarmv10.model.custom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupAlarm (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var isActive: Boolean = true,
    var name: String = ""
)