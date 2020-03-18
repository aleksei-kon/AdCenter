package com.adcenter.features.details.repository

import com.adcenter.api.getDetailsUrl
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result

class DetailsRepository(private val processor: DetailsProcessor) : IDetailsRepository {

    override fun getDetails(params: DetailsRequestParams): Result<DetailsModel> =
        runCatching {
            val request = NetworkDataRequest(
                getDetailsUrl(
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
