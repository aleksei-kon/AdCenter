package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    temporaryModule,
    myAdsDependencyModule,
    bookmarksDependencyModule,
    adRequestsDependencyModule,
    searchDependencyModule,
    detailsDependencyModule,
    newDetailsDependencyModule,
    loginDependencyModule,
    registrationDependencyModule,
    processorDependencyModule
)