package com.nagl.memesschedule.data.source.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nagl.memesschedule.data.model.UniPair

@Entity(tableName = "schedule")
data class DBSchedule(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val isCurrentWeekEven: Boolean,

    val evenWeek: List<UniPair>,

    val oddWeek: List<UniPair>,
)