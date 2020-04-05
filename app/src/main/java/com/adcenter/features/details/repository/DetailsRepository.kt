package com.adcenter.features.details.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.DetailsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams

class DetailsRepository(
    private val advertService: AdvertService,
    private val detailsMapper: DetailsMapper
) : IDetailsRepository {

    override fun getDetails(params: DetailsRequestParams): Result<DetailsModel> =
        runCatching {
            val networkResponse = advertService
                .getDetails(params.detailsId)
                .execute()
                .body()

            val response = detailsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
