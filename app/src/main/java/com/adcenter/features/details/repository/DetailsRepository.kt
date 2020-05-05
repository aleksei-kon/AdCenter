package com.adcenter.features.details.repository

import com.adcenter.datasource.database.DetailsDao
import com.adcenter.datasource.mappers.DetailsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.entities.database.DetailsDbEntity
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams

class DetailsRepository(
    private val advertsService: AdvertsService,
    private val detailsDao: DetailsDao,
    private val detailsMapper: DetailsMapper
) : IDetailsRepository {

    override fun getDetails(params: DetailsRequestParams): Result<DetailsModel> =
        runCatching {
            val networkResponse = advertsService
                .getDetails(params.detailsId)
                .execute()
                .body()

            networkResponse?.let {
                detailsDao.insert(DetailsDbEntity(it))
            }

            val response = detailsMapper.toDetailsModel(
                detailsDao.getDetails(params.detailsId)
            )

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }

    override fun showHide(advertId: Int): Result<Boolean> =
        runCatching {
            val networkResponse = advertsService
                .showHideAdvert(advertId)
                .execute()

            Result.Success(networkResponse.code() == 202)
        }.getOrElse {
            Result.Error(it)
        }

    override fun addDeleteBookmark(advertId: Int): Result<Boolean> =
        runCatching {
            val networkResponse = advertsService
                .addDeleteBookmark(advertId)
                .execute()

            Result.Success(networkResponse.code() == 202)
        }.getOrElse {
            Result.Error(it)
        }

    override fun delete(advertId: Int): Result<Boolean> =
        runCatching {
            val networkResponse = advertsService
                .deleteDetails(advertId)
                .execute()

            Result.Success(networkResponse.code() == 202)
        }.getOrElse {
            Result.Error(it)
        }
}
