package com.nagl.memesschedule.data.source.net

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.utils.Result

interface INetworkInteractor {

    suspend fun getUserInfo(requestData: UserRequest): Result<User>

    suspend fun getUserScheduleByGroup(group: String): Result<Schedule>
}