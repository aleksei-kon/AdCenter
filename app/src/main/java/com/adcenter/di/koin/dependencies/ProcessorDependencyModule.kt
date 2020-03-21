package com.adcenter.di.koin.dependencies

import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.data.processors.PhotoProcessor
import org.koin.dsl.module

val processorDependencyModule = module {
    factory { PhotoProcessor() }
    factory { NewDetailsProcessor() }
    factory { AppConfigProcessor() }
    factory { DetailsProcessor() }
}