package com.adcenter.features.adrequests.repository

import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.models.AdRequestsParams

class AdRequestsRepository(
    private val advertsService: AdvertsService,
    private val advertsDao: AdvertsDao,
    private val advertsMapper: AdvertsMapper
) : IAdRequestsRepository {

    override fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertsService
                .getAdRequests(params.pageNumber, params.isForceRefresh)
                .execute()
                .body()

            if (networkResponse != null && params.isForceRefresh) {
                advertsDao.clear()
            }

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
