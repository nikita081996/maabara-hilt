package com.covidkanzidata.dashboard.model.detailRequest


import com.squareup.moshi.Json

data class Request(
    @Json(name = "limit")
    var limit: Int = 1,
    @Json(name = "offset")
    var offset: Int = 1,
    @Json(name = "query")
    var query: List<Query>,
    @Json(name = "sort")
    var sort: List<Sort>
)


