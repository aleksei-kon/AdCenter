package com.adcenter.data.processors

import com.adcenter.features.newdetails.data.NewDetailsModel

class NewDetailsProcessor : IDataProcessor<NewDetailsModel> {

    override fun processResponse(response: String): NewDetailsModel {
        return NewDetailsModel(response.toBoolean())
    }
}