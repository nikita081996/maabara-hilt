package com.covidkanzidata.service

import com.covidkanzidata.dashboard.model.SessionResponse
import com.covidkanzidata.dashboard.model.detailRequest.Request
import com.covidkanzidata.dashboard.model.details.DataResponse
import com.covidkanzidata.utility.ApiConstants
import retrofit2.Response
import retrofit2.http.*

/**
 * API access points
 */
interface ApiService {

    @POST(ApiConstants.URL_SESSIONS)
    suspend fun getToken(
        @Header(ApiConstants.AUTHORIZATION) authorization: String,
        @Header(ApiConstants.ContentType) contentType: String
    ): Response<SessionResponse>

    @POST(ApiConstants.URL_DATA)
    suspend fun getData(
        @Header(ApiConstants.AUTHORIZATION) authorization: String,
        @Header(ApiConstants.ContentType) contentType: String,
        @Body request: Request
    ): Response<DataResponse>

}
