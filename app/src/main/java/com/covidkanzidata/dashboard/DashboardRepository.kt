package com.covidkanzidata.dashboard

import com.covidkanzidata.dashboard.model.detailRequest.Request
import com.covidkanzidata.service.resultLiveData
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val remoteSource: DashboardRemoteDataSource) {

    fun getToken() = resultLiveData(
        networkCall = { remoteSource.getToken() })

    fun getDetails(request: Request) = resultLiveData(
        networkCall = { remoteSource.getDetails(request) })

}