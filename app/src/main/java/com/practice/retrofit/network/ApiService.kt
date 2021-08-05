package com.practice.retrofit.network

import com.practice.retrofit.model.Account
import com.practice.retrofit.model.DefaultResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String? = null,
        @Field("password") password: String? = null,
    ): Response<DefaultResponse<Account>>
}