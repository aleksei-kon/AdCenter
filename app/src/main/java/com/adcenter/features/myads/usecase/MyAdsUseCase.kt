package com.adcenter.features.myads.usecase

import com.adcenter.features.myads.models.MyAdsModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.features.myads.repository.IMyAdsRepository
import com.adcenter.datasource.Result

class MyAdsUseCase(private val repository: IMyAdsRepository) : IMyAdsUseCase {

    override fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel> =
        Result.Success(loadModel(requestParams))

    private fun loadModel(requestParams: MyAdsRequestParams): MyAdsModel {
        val ads = when (val resutl = repository.getMyAds(requestParams)) {
            is Result.Success -> resutl.value
            is Result.Error -> emptyList()
        }

        return MyAdsModel(ads)
    }
}