package com.adcenter.di.koin.dependencies

import com.adcenter.data.processors.*
import org.koin.dsl.module

val processorDependencyModule = module {
    factory { AdsDataProcessor() }
    factory { PhotoProcessor() }
    factory { NewDetailsProcessor() }
    factory { AppConfigProcessor() }
    factory { DetailsProcessor() }
}