package com.adcenter.features.myads.usecase

import com.adcenter.entities.Result
import com.adcenter.features.myads.models.MyAdsModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.features.myads.repository.IMyAdsRepository

class MyAdsUseCase(private val repository: IMyAdsRepository) : IMyAdsUseCase {

    override fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel> =
        when (val resutl = repository.getMyAds(requestParams)) {
            is Result.Success -> Result.Success(MyAdsModel(resutl.value))
            is Result.Error -> Result.Error(resutl.exception)
        }

    override fun clearDb() {
        repository.clearDb()
    }
}