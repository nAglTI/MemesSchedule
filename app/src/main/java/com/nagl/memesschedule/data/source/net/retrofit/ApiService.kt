package com.nagl.memesschedule.data.source.net.retrofit

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.utils.Urls
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST(Urls.AUTH_URL)
    suspend fun getUserInfo(@Body userRequest: UserRequest): Response<User>

    @GET(Urls.SCHEDULE_URL + "/{group}")
    suspend fun getUserScheduleByGroup(@Path("group") group: String): Response<Schedule>
}