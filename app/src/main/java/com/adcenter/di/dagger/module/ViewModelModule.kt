package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adcenter.di.dagger.factories.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(
        map: Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>
    ): ViewModelProvider.Factory = ViewModelFactory(map)
}