package com.covidkanzidata.dashboard.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.covidkanzidata.dashboard.DashboardRepository
import com.covidkanzidata.dashboard.model.SessionResponse
import com.covidkanzidata.dashboard.model.detailRequest.Query
import com.covidkanzidata.dashboard.model.detailRequest.Request
import com.covidkanzidata.dashboard.model.detailRequest.Sort
import com.covidkanzidata.dashboard.model.details.DataResponse
import com.covidkanzidata.service.Result
import javax.inject.Inject

class ResultViewModel @ViewModelInject constructor(repository: DashboardRepository) : ViewModel() {

    internal val getToken: LiveData<Result<SessionResponse>> = repository.getToken()

    internal val getDetails: (Request) -> LiveData<Result<DataResponse>> = { request ->
        repository.getDetails(request)
    }

    /**
     * create request body for detail api
     * barcode is the scanned barcode value
     */
    internal fun createRequest(barcode: String): Request {
        val query1 = Query("=$barcode", null)
        val query2 = Query(null, "=$barcode")
        val sort = Sort()

        val query = mutableListOf<Query>()
        query.add(query1)
        query.add(query2)

        val sorts = mutableListOf<Sort>()
        sorts.add(sort)

        return Request(query = query, sort = sorts)
    }
}