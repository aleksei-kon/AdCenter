package com.adcenter.data

import com.adcenter.config.AppConfig
import com.adcenter.utils.Constants.EMPTY
import com.adcenter.utils.Constants.Request.AUTHORIZATION_HEADER
import com.adcenter.utils.Constants.Request.FILE_FORM_PARAM
import com.adcenter.utils.Constants.Request.MEDIA_TYPE_IMAGE
import com.adcenter.utils.Constants.Request.MEDIA_TYPE_JSON
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class NetworkDataRequest(
    private val url: String,
    private val body: Any? = null
) : IDataRequest, KoinComponent {

    private val client: OkHttpClient by inject()

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
        AppConfig.token?.let { requestBuilder.addHeader(AUTHORIZATION_HEADER, it) }
        requestBody?.let { requestBuilder.post(it) }

        val response = client
            .newCall(requestBuilder.build())
            .execute()

        return response.body()?.string() ?: EMPTY
    }
}
