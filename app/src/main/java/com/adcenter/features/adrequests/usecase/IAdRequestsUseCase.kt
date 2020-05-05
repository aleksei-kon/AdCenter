package com.adcenter.features.adrequests.usecase

import com.adcenter.features.adrequests.models.AdRequestsModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.entities.Result

interface IAdRequestsUseCase {

    fun load(requestParams: AdRequestsParams): Result<AdRequestsModel>

    fun clearDb()
}