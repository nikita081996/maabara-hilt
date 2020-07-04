package com.covidkanzidata.dashboard.model.detailRequest


import com.squareup.moshi.Json

data class Query(
    @Json(name = "Tests.BOOKING::additional_field_14" )
    var testsBOOKINGAdditionalField14: String?,
    @Json(name = "Tests.BOOKING::code")
    var testsBOOKINGCode: String?
)


