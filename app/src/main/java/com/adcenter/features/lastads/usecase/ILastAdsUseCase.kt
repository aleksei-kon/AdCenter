package com.adcenter.features.lastads.usecase

import com.adcenter.features.lastads.data.LastAdsModel
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result

interface ILastAdsUseCase {

    suspend fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel>
}
