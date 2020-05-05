package com.adcenter.features.lastads.usecase

import com.adcenter.features.lastads.models.LastAdsModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.features.lastads.repository.ILastAdsRepository
import com.adcenter.entities.Result

class LastAdsUseCase(private val repository: ILastAdsRepository) :
    ILastAdsUseCase {

    override fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel> =
        Result.Success(loadModel(requestParams))

    override fun clearDb() {
        repository.clearDb()
    }

    private fun loadModel(requestParams: LastAdsRequestParams): LastAdsModel {
        val ads = when (val lastAds = repository.getLastAds(requestParams)) {
            is Result.Success -> lastAds.value
            is Result.Error -> emptyList()
        }

        return LastAdsModel(ads)
    }

}