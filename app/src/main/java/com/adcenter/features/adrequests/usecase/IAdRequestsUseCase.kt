package com.adcenter.features.adrequests.usecase

import com.adcenter.features.adrequests.data.AdRequestsModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.utils.Result

interface IAdRequestsUseCase {

    suspend fun load(requestParams: AdRequestsParams): Result<AdRequestsModel>
}