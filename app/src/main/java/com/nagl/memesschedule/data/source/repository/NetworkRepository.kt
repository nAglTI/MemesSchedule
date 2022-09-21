package com.nagl.memesschedule.data.source.repository

import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.data.source.net.INetworkInteractor
import com.nagl.memesschedule.di.scope.IoDispatcher
import com.nagl.memesschedule.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkInteractor: INetworkInteractor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): INetworkRepository {

    override suspend fun getUserInfo(requestData: UserRequest): Result<User> =
        withContext(ioDispatcher) {
            when (val response = networkInteractor.getUserInfo(requestData)) {
                is Result.Success -> {
                    response
                }
                is Result.Error -> response
                else -> Result.Loading
            }
        }
}