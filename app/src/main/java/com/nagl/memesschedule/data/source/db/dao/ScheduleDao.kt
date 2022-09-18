package com.nagl.memesschedule.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nagl.memesschedule.data.source.db.entity.DBSchedule

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(vararg dbWeather: DBSchedule)

    @Query("SELECT * FROM schedule ORDER BY id DESC LIMIT 1")
    suspend fun getSchedule(): DBSchedule

    @Query("DELETE FROM schedule")
    suspend fun deleteSchedule()
}