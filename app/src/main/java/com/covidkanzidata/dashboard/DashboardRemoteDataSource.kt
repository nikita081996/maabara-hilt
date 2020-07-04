package com.covidkanzidata.dashboard

import com.covidkanzidata.dashboard.model.detailRequest.Request
import com.covidkanzidata.service.ApiService
import com.covidkanzidata.service.BaseDataSource
import com.covidkanzidata.utility.ApiConstants
import com.covidkanzidata.utility.SessionObject
import javax.inject.Inject

class DashboardRemoteDataSource @Inject constructor(private val service: ApiService) :
    BaseDataSource() {

    suspend fun getToken() = getResult {
        service.getToken(
            ApiConstants.AUTH_BASIC,
            ApiConstants.CONTENT_TYPE
        )
    }

    suspend fun getDetails(request: Request) = getResult {
        service.getData(
            SessionObject.authToken!!,
            ApiConstants.CONTENT_TYPE,
            request
        )
    }

}
