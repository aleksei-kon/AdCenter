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
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LastAdsViewModel(private val lastAdsUseCase: ILastAdsUseCase) : ViewModel() {

    private var currentParams: LastAdsRequestParams = LastAdsRequestParams()
    private var lastAdsModel: LastAdsModel = LastAdsModel()
    private var disposable: Disposable? = null

    private val nextPageSource: Single<LastAdsModel>
        get() = Single.create {
            when (val result = lastAdsUseCase.load(currentParams)) {
                is Result.Success -> {
                    lastAdsModel = mergeResults(lastAdsModel, result.value)
                    it.onSuccess(lastAdsModel)
                }
                is Result.Error -> {
                    it.onError(result.exception)
                }
            }
        }

    private val successConsumer: (LastAdsModel) -> Unit = {
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

        disposable = nextPageSource
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(successConsumer, errorConsumer)
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

        disposable?.dispose()
    }
}