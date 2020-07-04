package com.covidkanzidata.dashboard.model.details


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class FieldData(
    @Json(name = "ID")
    val iD: String? = null,
    @Json(name = "TestDate")
    val testDate: String? = null,
    @Json(name = "TestResult")
    val testResult: String? = null,
    @Json(name = "Tests.BOOKING::additional_field_14")
    val testsBOOKINGAdditionalField14: String? = null,
    @Json(name = "Tests.BOOKING::c_Name")
    val testsBOOKINGCName: String? = null,
    @Json(name = "Tests.BOOKING::code")
    val testsBOOKINGCode: String? = null,
    @Json(name = "ValidUpto")
    val validUpto: String? = null
) : Parcelable