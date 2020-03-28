package com.adcenter.di.dagger.module

import android.content.Context
import androidx.lifecycle.ViewModel
import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.processors.NewDetailsProcessor
import com.adcenter.datasource.processors.PhotoProcessor
import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.features.newdetails.repository.IPhotoRepository
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.newdetails.repository.PhotoRepository
import com.adcenter.features.newdetails.usecase.INewDetailsUseCase
import com.adcenter.features.newdetails.usecase.IUploadPhotoUseCase
import com.adcenter.features.newdetails.usecase.NewDetailsUseCase
import com.adcenter.features.newdetails.usecase.UploadPhotoUseCase
import com.adcenter.features.newdetails.viewmodel.NewDetailsViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class NewDetailsModule {

    @Provides
    @ActivityScope
    fun provideNewDetailsRepository(
        processor: NewDetailsProcessor,
        gson: Gson,
        api: IApi
    ): INewDetailsRepository =
        NewDetailsRepository(processor, gson, api)

    @Provides
    @ActivityScope
    fun providePhotoRepository(
        processor: PhotoProcessor,
        api: IApi
    ): IPhotoRepository =
        PhotoRepository(processor, api)

    @Provides
    @ActivityScope
    fun provideNewDetailsUseCase(
        repository: INewDetailsRepository
    ): INewDetailsUseCase =
        NewDetailsUseCase(repository)

    @Provides
    @ActivityScope
    fun provideUploadPhotoUseCase(
        repository: IPhotoRepository
    ): IUploadPhotoUseCase =
        UploadPhotoUseCase(repository)

    @Provides
    @IntoMap
    @ActivityScope
    @ViewModelKey(NewDetailsViewModel::class)
    fun provideNewDetailsViewModel(
        context: Context,
        newDetailsUseCase: INewDetailsUseCase,
        uploadPhotoUseCase: IUploadPhotoUseCase
    ): ViewModel = NewDetailsViewModel(context, newDetailsUseCase, uploadPhotoUseCase)
}