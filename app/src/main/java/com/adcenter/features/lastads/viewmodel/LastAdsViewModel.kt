package com.adcenter.features.lastads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.LastAdsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.lastads.data.LastAdsModel
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.features.lastads.uistate.LastAdsUiState
import com.adcenter.features.lastads.usecase.ILastAdsUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LastAdsViewModel(private val lastAdsUseCase: ILastAdsUseCase) : ViewModel(), CoroutineScope {

    private var currentParams: LastAdsRequestParams = LastAdsRequestParams()
    private var lastAdsModel: LastAdsModel = LastAdsModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val lastAdsUiMutableState = MutableLiveData<LastAdsUiState>()

    val lastAdsData: LiveData<LastAdsUiState>
        get() = lastAdsUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        if (pageNumber == FIRST_PAGE_NUMBER) {
            lastAdsUiMutableState.value = LastAdsUiState.Loading
            loadModel()
        } else {
            lastAdsUiMutableState.value = LastAdsUiState.Success(lastAdsModel)
        }
    }

    fun loadMore() {
        lastAdsUiMutableState.value = LastAdsUiState.Pagination
        loadModel()
    }

    fun refresh() {
        lastAdsModel = LastAdsModel()
        pageNumber = FIRST_PAGE_NUMBER
        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        currentParams = LastAdsRequestParams(pageNumber)

        launch {
            when (val result = lastAdsUseCase.load(currentParams)) {
                is Result.Success -> {
                    lastAdsModel = mergeResults(lastAdsModel, result.value)

                    lastAdsUiMutableState.value = LastAdsUiState.Success(lastAdsModel)

                    pageNumber++
                }
                is Result.Error -> {
                    lastAdsUiMutableState.value = LastAdsUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: LastAdsModel,
        newResponse: LastAdsModel
    ): LastAdsModel {
        val itemsList = mutableListOf<AdItemModel>()

        itemsList += oldResponse.ads
        itemsList += newResponse.ads

        return LastAdsModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}