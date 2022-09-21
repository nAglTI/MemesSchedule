package com.nagl.memesschedule.data.source.net.retrofit

import com.nagl.memesschedule.data.model.User
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.utils.Urls
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(Urls.AUTH_URL)
    suspend fun getUserInfo(@Body userRequest: UserRequest): Response<User>
}