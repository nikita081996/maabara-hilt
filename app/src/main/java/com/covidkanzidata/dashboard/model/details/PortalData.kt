package com.covidkanzidata.dashboard.model.details


import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
class PortalData : Parcelable