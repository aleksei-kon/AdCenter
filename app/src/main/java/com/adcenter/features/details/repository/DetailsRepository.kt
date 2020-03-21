package com.adcenter.features.details.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result
import javax.inject.Inject

class DetailsRepository(private val processor: DetailsProcessor) : IDetailsRepository {

    @Inject
    lateinit var api: IApi

    init {
        Injector.appComponent.inject(this)
    }

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
