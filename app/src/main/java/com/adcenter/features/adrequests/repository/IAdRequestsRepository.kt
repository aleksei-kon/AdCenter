package com.adcenter.features.adrequests.repository

import com.adcenter.entities.Result
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.models.AdRequestsParams

interface IAdRequestsRepository {

    fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>>

    fun clearDb()
}