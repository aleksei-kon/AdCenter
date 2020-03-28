package com.adcenter.features.adrequests.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.datasource.Result

interface IAdRequestsRepository {

    fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>>
}