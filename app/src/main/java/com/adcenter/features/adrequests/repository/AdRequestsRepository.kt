package com.adcenter.features.adrequests.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.models.AdRequestsParams

class AdRequestsRepository(
    private val advertService: AdvertService,
    private val adsMapper: AdsMapper
) : IAdRequestsRepository {

    override fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertService
                .getAdRequests(params.pageNumber)
                .execute()
                .body()

            val response = adsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
