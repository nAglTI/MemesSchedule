package com.nagl.memesschedule.data.source.net

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.data.source.net.retrofit.ApiService
import com.nagl.memesschedule.di.scope.IoDispatcher
import com.nagl.memesschedule.utils.Result
import com.nagl.memesschedule.utils.Urls
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkInteractor @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : INetworkInteractor {

    override suspend fun getUserInfo(requestData: UserRequest): Result<User> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getUserInfo(requestData)
                if (result.isSuccessful) {
                    val userInfo = result.body()
                    Result.Success(userInfo)
                } else {
                    Result.Success(null)
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }

    override suspend fun getUserScheduleByGroup(group: String): Result<Schedule> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = apiService.getUserScheduleByGroup(group)
                if (result.isSuccessful) {
                    val schedule = result.body()
                    Result.Success(schedule)
                } else {
                    Result.Success(null)
                }
            } catch (ex: Exception) {
                Result.Error(ex)
            }
        }

}