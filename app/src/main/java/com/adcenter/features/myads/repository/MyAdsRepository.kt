package com.adcenter.features.myads.repository

import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.models.MyAdsRequestParams

class MyAdsRepository(
    private val advertsService: AdvertsService,
    private val advertsDao: AdvertsDao,
    private val advertsMapper: AdvertsMapper
) : IMyAdsRepository {

    override fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertsService
                .getMyAds(params.pageNumber)
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

    override fun clearDb() {
        advertsDao.clear()
    }
}
