package com.nagl.memesschedule.data.source.db

import com.nagl.memesschedule.data.source.db.entity.DBSchedule

interface IDatabaseInteractor {

    suspend fun getSchedule(): DBSchedule?

    suspend fun saveSchedule(schedule: DBSchedule)

    suspend fun deleteSchedule()
}