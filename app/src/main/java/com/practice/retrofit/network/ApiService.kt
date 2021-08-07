package com.practice.retrofit.network

import com.practice.retrofit.model.Account
import com.practice.retrofit.model.DefaultResponse
import com.practice.retrofit.model.Store
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String? = null,
        @Field("password") password: String? = null,
    ): Response<DefaultResponse<Account>>

    @GET("store")
    suspend fun allStore(): Response<DefaultResponse<List<Store>>>

    @GET("store/find?")
    suspend fun findStore(
        @Query("startlat") startlat: String? = null,
        @Query("endlat") endlat: String? = null,
        @Query("startlong") startlong: String? = null,
        @Query("endlong") endlong: String? = null,
    ): Response<DefaultResponse<List<Store>>>
}