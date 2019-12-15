package com.adcenter.features.adrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.AdRequestsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.adrequests.data.AdRequestsModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.features.adrequests.uistate.AdRequestsUiState
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AdRequestsViewModel(private val adRequestsUseCase: IAdRequestsUseCase) : ViewModel(),
    CoroutineScope {

    private var currentParams: AdRequestsParams = AdRequestsParams()
    private var adRequestsModel: AdRequestsModel = AdRequestsModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val adRequestsUiMutableState = MutableLiveData<AdRequestsUiState>()

    val adRequestsData: LiveData<AdRequestsUiState>
        get() = adRequestsUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        adRequestsUiMutableState.value = AdRequestsUiState.Loading
        loadModel()
    }

    fun loadMore() {
        loadModel()
    }

    fun refresh() {
        adRequestsModel = AdRequestsModel()
        pageNumber = FIRST_PAGE_NUMBER
        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        currentParams = AdRequestsParams(pageNumber)

        launch {
            when (val result = adRequestsUseCase.load(currentParams)) {
                is Result.Success -> {
                    adRequestsModel = mergeResults(adRequestsModel, result.value)

                    adRequestsUiMutableState.value = AdRequestsUiState.Success(adRequestsModel)

                    pageNumber++
                }
                is Result.Error -> {
                    adRequestsUiMutableState.value = AdRequestsUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: AdRequestsModel,
        newResponse: AdRequestsModel
    ): AdRequestsModel {
        val itemsList = mutableListOf<AdItemModel>()

        itemsList += oldResponse.ads
        itemsList += newResponse.ads

        return AdRequestsModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}