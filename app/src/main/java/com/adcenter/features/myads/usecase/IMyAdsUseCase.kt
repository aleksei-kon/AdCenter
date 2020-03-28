package com.adcenter.features.myads.usecase

import com.adcenter.features.myads.models.MyAdsModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.datasource.Result

interface IMyAdsUseCase {

    fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel>
}
