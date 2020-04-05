package com.adcenter.features.lastads.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.models.LastAdsRequestParams

class LastAdsRepository(
    private val advertService: AdvertService,
    private val adsMapper: AdsMapper
) : ILastAdsRepository {

    override fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertService
                .getLastAds(params.pageNumber)
                .execute()
                .body()

            val response = adsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
