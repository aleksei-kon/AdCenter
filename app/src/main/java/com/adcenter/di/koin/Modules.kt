package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    temporaryModule,
    myAdsDependencyModule,
    detailsDependencyModule,
    newDetailsDependencyModule,
    loginDependencyModule,
    registrationDependencyModule,
    processorDependencyModule
)