package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    temporaryModule,
    detailsDependencyModule,
    newDetailsDependencyModule,
    loginDependencyModule,
    registrationDependencyModule,
    processorDependencyModule
)