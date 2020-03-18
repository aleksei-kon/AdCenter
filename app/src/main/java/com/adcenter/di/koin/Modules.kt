package com.adcenter.di.koin

import com.adcenter.di.koin.dependencies.*

val koinModules = listOf(
    appDependencyModule,
    lastAdsDependencyModule,
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