package com.adcenter.features.lastads.usecase

import com.adcenter.features.lastads.models.LastAdsModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.datasource.Result

interface ILastAdsUseCase {

    fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel>
}
