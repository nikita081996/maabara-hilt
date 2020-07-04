package com.covidkanzidata.dashboard.model.detailRequest


import com.squareup.moshi.Json

data class Sort(
    @Json(name = "fieldName")
    val fieldName: String = "zz_CreationTimestamp",
    @Json(name = "sortOrder")
    val sortOrder: String = "descend"
)


