package com.adcenter.features.lastads.usecase

import com.adcenter.entities.Result
import com.adcenter.features.lastads.models.LastAdsModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.features.lastads.repository.ILastAdsRepository

class LastAdsUseCase(private val repository: ILastAdsRepository) :
    ILastAdsUseCase {

    override fun load(requestParams: LastAdsRequestParams): Result<LastAdsModel> =
        when (val lastAds = repository.getLastAds(requestParams)) {
            is Result.Success -> Result.Success(LastAdsModel(lastAds.value))
            is Result.Error -> Result.Error(lastAds.exception)
        }

    override fun clearDb() {
        repository.clearDb()
    }
}