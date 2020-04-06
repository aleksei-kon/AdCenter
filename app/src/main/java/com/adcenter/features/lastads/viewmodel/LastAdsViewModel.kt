package com.adcenter.features.lastads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.lastads.LastAdsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.lastads.models.LastAdsModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.features.lastads.uistate.LastAdsUiState
import com.adcenter.features.lastads.usecase.ILastAdsUseCase
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class LastAdsViewModel(
    private val useCase: ILastAdsUseCase
) : ViewModel() {

    private var currentParams: LastAdsRequestParams = LastAdsRequestParams()
    private var lastAdsModel: LastAdsModel = LastAdsModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<LastAdsModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val successConsumer: (LastAdsModel) -> Unit = {
        lastAdsModel = it
        lastAdsUiMutableState.value = LastAdsUiState.Success(it)
        currentParams = currentParams.copy(
            pageNumber = currentParams.pageNumber + 1
        )
    }

    private val errorConsumer: (Throwable) -> Unit = {
        lastAdsUiMutableState.value = LastAdsUiState.Error(it)
    }

    private val lastAdsUiMutableState = MutableLiveData<LastAdsUiState>()

    val lastAdsData: LiveData<LastAdsUiState>
        get() = lastAdsUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
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
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER
        )
        loadModel()
    }

    private fun loadModel() {
        disposable?.dispose()

        disposable = dataSource
            .async()
            .subscribe(successConsumer, errorConsumer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}