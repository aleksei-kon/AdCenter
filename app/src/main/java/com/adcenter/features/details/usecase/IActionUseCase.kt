package com.adcenter.features.details.usecase

import com.adcenter.entities.Result

interface IActionUseCase {

    fun showHide(advertId: Int): Result<Boolean>

    fun addDeleteBookmark(advertId: Int): Result<Boolean>

    fun delete(advertId: Int): Result<Boolean>
}