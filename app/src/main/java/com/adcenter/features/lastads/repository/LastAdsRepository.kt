package com.adcenter.features.lastads.repository

import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.models.LastAdsRequestParams

class LastAdsRepository(
    private val advertsService: AdvertsService,
    private val advertsDao: AdvertsDao,
    private val advertsMapper: AdvertsMapper
) : ILastAdsRepository {

    override fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertsService
                .getLastAds(params.pageNumber)
                .execute()
                .body()

            networkResponse
                ?.map { AdItemDbEntity(it) }
                ?.toTypedArray()
                ?.let { advertsDao.insert(*it) }

            val response = advertsDao
                .getAdverts()
                .map { advertsMapper.toAdItem(it) }

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
