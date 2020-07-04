package com.covidkanzidata.dashboard.model


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class SessionResponse(
    @Json(name = "messages")
    val messages: List<Message>? = null,
    @Json(name = "response")
    val response: Response? = null
) : Parcelable