package com.adcenter.features.myads.usecase

import com.adcenter.features.myads.data.MyAdsModel
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.features.myads.repository.IMyAdsRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MyAdsUseCase(private val repository: IMyAdsRepository) : IMyAdsUseCase {

    override suspend fun load(requestParams: MyAdsRequestParams): Result<MyAdsModel> =
        Result.Success(loadModel(requestParams))

    private suspend fun loadModel(requestParams: MyAdsRequestParams): MyAdsModel {
        return coroutineScope {
            val adsAsync = async { repository.getMyAds(requestParams) }
            val adsResult = adsAsync.await()

            val ads = when (adsResult) {
                is Result.Success -> adsResult.value
                is Result.Error -> emptyList()
            }

            MyAdsModel(ads)
        }
    }
}