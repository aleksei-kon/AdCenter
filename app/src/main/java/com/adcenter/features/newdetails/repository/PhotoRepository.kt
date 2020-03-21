package com.adcenter.features.newdetails.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.PhotoProcessor
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.utils.Result
import java.io.File
import javax.inject.Inject

class PhotoRepository(private val processor: PhotoProcessor) : IPhotoRepository {

    @Inject
    lateinit var api: IApi

    init {
        Injector.appComponent.inject(this)
    }

    override fun addPhoto(file: File): Result<String> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getImageUploadUrl(),
                body = file
            )

            val response = Callable<String>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}