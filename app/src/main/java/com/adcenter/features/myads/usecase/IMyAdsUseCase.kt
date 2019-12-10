package com.adcenter.features.myads.usecase

import com.adcenter.features.myads.data.MyAdsModel
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result

interface IMyAdsUseCase {

    suspend fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel>
}
