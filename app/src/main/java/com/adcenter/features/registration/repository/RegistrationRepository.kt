package com.adcenter.features.registration.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.utils.Result
import com.google.gson.Gson
import javax.inject.Inject

class RegistrationRepository(
    private val processor: AppConfigProcessor
) : IRegistrationRepository {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var api: IApi

    init {
        Injector.appComponent.inject(this)
    }

    override fun register(params: RegistrationRequestParams): Result<AppConfigInfo> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getRegisterUrl(),
                body = gson.toJson(params)
            )

            val response = Callable<AppConfigInfo>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}