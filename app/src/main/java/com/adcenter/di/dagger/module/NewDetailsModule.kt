package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.data.processors.PhotoProcessor
import com.adcenter.di.dagger.scopes.NewDetailsScope
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.features.newdetails.repository.IPhotoRepository
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.newdetails.repository.PhotoRepository
import com.adcenter.features.newdetails.usecase.INewDetailsUseCase
import com.adcenter.features.newdetails.usecase.IUploadPhotoUseCase
import com.adcenter.features.newdetails.usecase.NewDetailsUseCase
import com.adcenter.features.newdetails.usecase.UploadPhotoUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class NewDetailsModule {

    @Provides
    @NewDetailsScope
    fun provideNewDetailsRepository(
        processor: NewDetailsProcessor,
        gson: Gson,
        api: IApi
    ): INewDetailsRepository =
        NewDetailsRepository(processor, gson, api)

    @Provides
    @NewDetailsScope
    fun providePhotoRepository(
        processor: PhotoProcessor,
        api: IApi
    ): IPhotoRepository =
        PhotoRepository(processor, api)

    @Provides
    @NewDetailsScope
    fun provideNewDetailsUseCase(
        repository: INewDetailsRepository
    ): INewDetailsUseCase =
        NewDetailsUseCase(repository)

    @Provides
    @NewDetailsScope
    fun provideUploadPhotoUseCase(
        repository: IPhotoRepository
    ): IUploadPhotoUseCase =
        UploadPhotoUseCase(repository)
}