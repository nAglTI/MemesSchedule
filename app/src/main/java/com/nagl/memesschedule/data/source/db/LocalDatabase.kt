package com.nagl.memesschedule.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nagl.memesschedule.data.source.db.dao.ScheduleDao
import com.nagl.memesschedule.data.source.db.entity.DBSchedule
import com.nagl.memesschedule.data.source.db.typeconverters.ListUniPairConverter

@Database(
    entities = [
        DBSchedule::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    ListUniPairConverter::class
)
abstract class LocalDatabase : RoomDatabase() {

    abstract val scheduleDao: ScheduleDao
}