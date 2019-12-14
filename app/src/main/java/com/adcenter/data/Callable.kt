package com.adcenter.data

import com.adcenter.data.processors.IDataProcessor

class Callable<T> {

    private lateinit var request: IDataRequest
    private lateinit var processor: IDataProcessor<T>

    fun setRequest(request: IDataRequest): Callable<T> {
        this.request = request

        return this
    }

    fun setProcessor(processor: IDataProcessor<T>): Callable<T> {
        this.processor = processor

        return this
    }

    fun call(): T = processor.processResponse(request.getResponse())
}