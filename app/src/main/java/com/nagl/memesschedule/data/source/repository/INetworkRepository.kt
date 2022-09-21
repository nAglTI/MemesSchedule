package com.nagl.memesschedule.data.source.repository

import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.utils.Result

interface INetworkRepository {

    suspend fun getUserInfo(requestData: UserRequest): Result<User>
}