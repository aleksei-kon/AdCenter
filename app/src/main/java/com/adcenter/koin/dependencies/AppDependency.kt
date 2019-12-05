package com.adcenter.koin.dependencies

import com.adcenter.utils.IResourceDependency
import com.adcenter.utils.ResourceDependency
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppDependency {
    val module = module {
        single<IResourceDependency> {
            ResourceDependency(
                androidContext()
            )
        }
    }
}