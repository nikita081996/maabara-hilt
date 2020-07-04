package com.covidkanzidata.dashboard.model.details


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "code")
    val code: String? = null,
    @Json(name = "message")
    val message: String? = null
) : Parcelable