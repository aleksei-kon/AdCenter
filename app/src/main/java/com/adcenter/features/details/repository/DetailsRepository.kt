package com.adcenter.features.details.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.DetailsProcessor
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.datasource.Result

class DetailsRepository(
    private val processor: DetailsProcessor,
    private val api: IApi
) : IDetailsRepository {

    override fun getDetails(params: DetailsRequestParams): Result<DetailsModel> =
        runCatching {
            val request = NetworkDataRequest(
                api.getDetailsUrl(
                    params
                )
            )

            val response = Callable<DetailsModel>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
