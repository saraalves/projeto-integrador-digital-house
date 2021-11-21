package com.jenandsara.marvelapp.v2.data.model

import com.google.gson.annotations.SerializedName

data class DataModel<T>(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<T>
)


