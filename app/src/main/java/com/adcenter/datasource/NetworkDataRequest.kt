package com.adcenter.datasource

import com.adcenter.appconfig.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.Request.AUTHORIZATION_HEADER
import com.adcenter.extensions.Constants.Request.FILE_FORM_PARAM
import com.adcenter.extensions.Constants.Request.MEDIA_TYPE_IMAGE
import com.adcenter.extensions.Constants.Request.MEDIA_TYPE_JSON
import okhttp3.*
import java.io.File
import javax.inject.Inject

class NetworkDataRequest(
    private val url: String,
    private val body: Any? = null
) : IDataRequest {

    @Inject
    lateinit var appConfig: IAppConfig

    @Inject
    lateinit var client: OkHttpClient

    init {
        Injector.appComponent.inject(this)
    }

    override fun getResponse(): String {
        return execute()
    }

    private fun execute(): String {
        val requestBody: RequestBody? = when (body) {
            is String -> {
                RequestBody.create(MediaType.parse(MEDIA_TYPE_JSON), body)
            }
            is File -> {
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        FILE_FORM_PARAM,
                        body.name,
                        RequestBody.create(MediaType.parse(MEDIA_TYPE_IMAGE), body)
                    )
                    .build()
            }
            else -> null
        }

        val requestBuilder = Request.Builder().url(url)
        appConfig.token?.let { requestBuilder.addHeader(AUTHORIZATION_HEADER, it) }
        requestBody?.let { requestBuilder.post(it) }

        val response = client
            .newCall(requestBuilder.build())
            .execute()

        return response.body()?.string() ?: EMPTY
    }
}
