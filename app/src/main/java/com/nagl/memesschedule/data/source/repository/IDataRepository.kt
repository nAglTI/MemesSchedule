package com.nagl.memesschedule.data.source.repository

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.utils.Result

interface IDataRepository {

    suspend fun getUserInfo(requestData: UserRequest): Result<User>

    suspend fun getUserScheduleByGroup(group: String, refresh: Boolean): Result<Schedule>

    suspend fun saveSchedule(schedule: Schedule)

    suspend fun deleteSchedule()
}