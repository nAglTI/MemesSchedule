package com.nagl.memesschedule.data.source.db

import com.nagl.memesschedule.data.source.db.dao.ScheduleDao
import com.nagl.memesschedule.data.source.db.entity.DBSchedule
import com.nagl.memesschedule.di.scope.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val scheduleDao: ScheduleDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : IDatabaseInteractor {

    override suspend fun getSchedule(): DBSchedule =
        withContext(ioDispatcher) {
            return@withContext scheduleDao.getSchedule()
        }

    override suspend fun saveSchedule(schedule: DBSchedule) =
        withContext(ioDispatcher) {
            scheduleDao.insertSchedule(schedule)
        }

    override suspend fun deleteSchedule() =
        withContext(ioDispatcher) {
            scheduleDao.deleteSchedule()
        }

}