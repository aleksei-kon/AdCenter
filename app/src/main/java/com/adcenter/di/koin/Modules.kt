package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    temporaryModule,
    newDetailsDependencyModule,
    processorDependencyModule
)