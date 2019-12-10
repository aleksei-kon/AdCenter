package com.adcenter.myads.usecase

import com.adcenter.myads.data.MyAdsModel
import com.adcenter.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result

interface IMyAdsUseCase {

    suspend fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel>
}
