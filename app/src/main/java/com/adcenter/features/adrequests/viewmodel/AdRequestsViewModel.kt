package com.adcenter.features.adrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.extensions.async
import com.adcenter.features.adrequests.AdRequestsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.adrequests.models.AdRequestsModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.features.adrequests.uistate.AdRequestsUiState
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import com.adcenter.datasource.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class AdRequestsViewModel(
    private val adRequestsUseCase: IAdRequestsUseCase
) : ViewModel() {

    private var currentParams: AdRequestsParams = AdRequestsParams()
    private var adRequestsModel: AdRequestsModel = AdRequestsModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<AdRequestsModel>
        get() = Single.create {
            when (val result = adRequestsUseCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(updateModel(result.value))
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<AdRequestsModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: AdRequestsModel) {
            adRequestsUiMutableState.value = AdRequestsUiState.Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1
            )
        }

        override fun onError(e: Throwable) {
            adRequestsUiMutableState.value = AdRequestsUiState.Error(e)
        }
    }

    private val adRequestsUiMutableState = MutableLiveData<AdRequestsUiState>()

    val adRequestsData: LiveData<AdRequestsUiState>
        get() = adRequestsUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            adRequestsUiMutableState.value = AdRequestsUiState.Loading
            loadModel()
        } else {
            adRequestsUiMutableState.value = AdRequestsUiState.Success(adRequestsModel)
        }
    }

    fun loadMore() {
        adRequestsUiMutableState.value = AdRequestsUiState.Pagination
        loadModel()
    }

    fun refresh() {
        adRequestsModel = AdRequestsModel()
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER
        )

        loadModel()
    }

    private fun loadModel() {
        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    private fun updateModel(
        newResponse: AdRequestsModel
    ): AdRequestsModel {
        adRequestsModel = AdRequestsModel(adRequestsModel.ads + newResponse.ads)

        return adRequestsModel
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}