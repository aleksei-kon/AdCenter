package com.adcenter.features.details.usecase

import com.adcenter.features.details.repository.IDetailsRepository

class ActionUseCase(private val repository: IDetailsRepository) : IActionUseCase {

    override fun showHide(advertId: Int) = repository.showHide(advertId)

    override fun addDeleteBookmark(advertId: Int) = repository.addDeleteBookmark(advertId)

    override fun delete(advertId: Int) = repository.delete(advertId)
}