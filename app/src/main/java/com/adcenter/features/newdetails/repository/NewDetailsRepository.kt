package com.adcenter.features.newdetails.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.NewDetailsProcessor
import com.adcenter.features.newdetails.models.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.datasource.Result
import com.google.gson.Gson

class NewDetailsRepository(
    private val processor: NewDetailsProcessor,
    private val gson: Gson,
    private val api: IApi
) : INewDetailsRepository {

    override fun addDetails(params: NewDetailsRequestParams): Result<NewDetailsModel> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getNewDetailsUrl(),
                body = gson.toJson(params)
            )

            val response = Callable<NewDetailsModel>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}