package com.nagl.memesschedule.data.source.repository

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.data.source.db.IDatabaseInteractor
import com.nagl.memesschedule.data.source.db.entity.DBSchedule
import com.nagl.memesschedule.data.source.net.INetworkInteractor
import com.nagl.memesschedule.di.scope.IoDispatcher
import com.nagl.memesschedule.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val networkInteractor: INetworkInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IDataRepository {

    override suspend fun getUserInfo(requestData: UserRequest): Result<User> =
        withContext(ioDispatcher) {
            when (val response = networkInteractor.getUserInfo(requestData)) {
                is Result.Success, is Result.Error -> response
                else -> Result.Loading
            }
        }

    override suspend fun getUserScheduleByGroup(group: String, refresh: Boolean): Result<Schedule> =
        withContext(ioDispatcher) {
            if (refresh) {
                when (val response = networkInteractor.getUserScheduleByGroup(group)) {
                    is Result.Success, is Result.Error -> response
                    else -> Result.Loading
                }
            } else {
                val schedule = databaseInteractor.getSchedule()
                if (schedule != null) {
                    Result.Success(
                        Schedule(
                            schedule.isCurrentWeekEven,
                            schedule.evenWeek,
                            schedule.oddWeek
                        )
                    )
                } else {
                    Result.Success(null)
                }
            }
        }

    override suspend fun saveSchedule(schedule: Schedule) =
        withContext(ioDispatcher) {
            databaseInteractor.saveSchedule(
                DBSchedule(
                    0,
                    schedule.isCurrentWeekEven,
                    schedule.evenWeek,
                    schedule.oddWeek
                )
            )
        }

    override suspend fun deleteSchedule() =
        withContext(ioDispatcher) {
            databaseInteractor.deleteSchedule()
        }
}