package com.adcenter.features.adrequests.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.utils.Result

interface IAdRequestsRepository {

    fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>>
}