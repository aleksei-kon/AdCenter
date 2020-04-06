package com.adcenter.features.newdetails.repository

import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.features.newdetails.models.NewDetailsRequestParams

class NewDetailsRepository(
    private val advertsService: AdvertsService
) : INewDetailsRepository {

    override fun addDetails(params: NewDetailsRequestParams): Result<Unit> =
        runCatching {
            val networkResponse = advertsService
                .addNewDetails(params.newDetailsModel)
                .execute()

            val response = when (networkResponse.code()) {
                200, 201 -> Unit
                else -> throw Exception("Not created")
            }

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}