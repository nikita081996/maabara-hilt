package com.covidkanzidata.dashboard.model.details


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class DataInfo(
    @Json(name = "database")
    val database: String? = null,
    @Json(name = "foundCount")
    val foundCount: Int? = null,
    @Json(name = "layout")
    val layout: String? = null,
    @Json(name = "returnedCount")
    val returnedCount: Int? = null,
    @Json(name = "table")
    val table: String? = null,
    @Json(name = "totalRecordCount")
    val totalRecordCount: Int? = null
) : Parcelable