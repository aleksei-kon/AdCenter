package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    temporaryModule,
    newDetailsDependencyModule,
    loginDependencyModule,
    registrationDependencyModule,
    processorDependencyModule
)