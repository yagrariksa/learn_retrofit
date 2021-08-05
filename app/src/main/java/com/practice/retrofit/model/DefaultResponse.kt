package com.practice.retrofit.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse<out T: Any?>(

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("body")
    val body: T? = null
)
