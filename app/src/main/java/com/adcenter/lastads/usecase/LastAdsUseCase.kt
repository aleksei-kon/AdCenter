package com.adcenter.lastads.usecase

import com.adcenter.lastads.data.LastAdsModel
import com.adcenter.lastads.data.LastAdsRequestParams
import com.adcenter.lastads.repository.ILastAdsRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LastAdsUseCase(private val repository: ILastAdsRepository) :
    ILastAdsUseCase {

    override suspend fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel> =
        Result.Success(loadModel(requestParams))

    private suspend fun loadModel(requestParams: LastAdsRequestParams): LastAdsModel {
        return coroutineScope {
            val adsAsync = async { repository.getLastAds(requestParams) }
            val adsResult = adsAsync.await()

            val ads = when (adsResult) {
                is Result.Success -> adsResult.value
                is Result.Error -> emptyList()
            }

            LastAdsModel(ads)
        }
    }

}