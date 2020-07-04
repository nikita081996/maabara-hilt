package com.covidkanzidata.dashboard.model.details


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "fieldData")
    val fieldData: FieldData? = null,
    @Json(name = "modId")
    val modId: String? = null,
    @Json(name = "portalData")
    val portalData: PortalData? = null,
    @Json(name = "recordId")
    val recordId: String? = null
) : Parcelable