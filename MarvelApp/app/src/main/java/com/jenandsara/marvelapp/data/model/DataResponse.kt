package com.jenandsara.marvelapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    @SerialName("offset") val offset: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("total") val total: Int,
    @SerialName("count") val count: Int,
    @SerialName("results") val results: List<T>
)


