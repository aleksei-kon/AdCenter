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

    private val lastAdsUiMutableState = MutableLiveData<LastAdsUiState>()

    private var disposable: Disposable? = null

    private val nextPageSource: Single<LastAdsUiState>
        get() = Single.create {
            val result = when (val info = lastAdsUseCase.load(currentParams)) {
                is Result.Success -> {
                    lastAdsModel = mergeResults(lastAdsModel, info.value)

                    LastAdsUiState.Success(lastAdsModel)
                }
                is Result.Error -> {
                    LastAdsUiState.Error(info.exception)
                }
            }

            it.onSuccess(result)
        }

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                lastAdsUiMutableState.value = result
                currentParams = currentParams.copy(
                    pageNumber = currentParams.pageNumber + 1
                )
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

        disposable?.dispose()
    }
}