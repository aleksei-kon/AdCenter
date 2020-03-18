package com.adcenter.features.newdetails.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.utils.Result
import com.google.gson.Gson
import javax.inject.Inject

class NewDetailsRepository(
    private val processor: NewDetailsProcessor
) : INewDetailsRepository {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var api: IApi

    init {
        App.appComponent.inject(this)
    }

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