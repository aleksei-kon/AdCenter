package com.adcenter.data.processors

interface IDataProcessor<T> {

    fun processResponse(response: String): T
}