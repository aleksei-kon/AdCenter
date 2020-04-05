package com.adcenter.features.myads.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.models.MyAdsRequestParams

class MyAdsRepository(
    private val advertService: AdvertService,
    private val adsMapper: AdsMapper
) : IMyAdsRepository {

    override fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertService
                .getMyAds(params.pageNumber)
                .execute()
                .body()

            val response = adsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
