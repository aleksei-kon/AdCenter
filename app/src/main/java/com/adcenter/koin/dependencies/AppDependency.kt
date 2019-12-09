package com.adcenter.koin.dependencies

import com.adcenter.utils.IResourceProvider
import com.adcenter.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppDependency {
    val module = module {
        single<IResourceProvider> {
            ResourceProvider(
                androidContext()
            )
        }
    }
}