package com.adcenter.lastads.usecase

import com.adcenter.lastads.data.LastAdsModel
import com.adcenter.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result

interface ILastAdsUseCase {

    suspend fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel>
}
