package com.practice.retrofit.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("lat")
    val lat: String? = null,

    @SerializedName("long")
    val long: String? = null,

    @SerializedName("name_store")
    val name: String? = null,
)
