package com.adcenter.koin.dependencies

import com.adcenter.data.processors.*
import org.koin.dsl.module

val processorDependencyModule = module {
    single { AdsDataProcessor(get()) }
    single { PhotoProcessor(get()) }
    single { NewDetailsProcessor(get()) }
    single { AppConfigProcessor(get()) }
    single {
        DetailsProcessor(
            gson = get(),
            resourceProvider = get()
        )
    }
}