package com.adcenter.di.koin.dependencies

import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.data.processors.PhotoProcessor
import org.koin.dsl.module

val processorDependencyModule = module {
    factory { PhotoProcessor() }
    factory { NewDetailsProcessor() }
}