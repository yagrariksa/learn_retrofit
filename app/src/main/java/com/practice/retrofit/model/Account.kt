package com.practice.retrofit.model

import com.google.gson.annotations.SerializedName

data class Account(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("api_token")
    val api: String? = null,
)
