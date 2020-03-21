package com.adcenter.features.newdetails.repository

import com.adcenter.api.IApi
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.utils.Result
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