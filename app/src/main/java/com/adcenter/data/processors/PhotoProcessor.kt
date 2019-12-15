package com.adcenter.data.processors

class PhotoProcessor : IDataProcessor<String> {

    override fun processResponse(response: String): String = response
}