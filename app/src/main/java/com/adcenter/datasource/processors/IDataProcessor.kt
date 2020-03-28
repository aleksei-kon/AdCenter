package com.adcenter.datasource.processors

interface IDataProcessor<T> {

    fun processResponse(response: String): T
}