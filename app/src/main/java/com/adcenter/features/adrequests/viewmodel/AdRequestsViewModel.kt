package com.adcenter.features.adrequests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.adrequests.AdRequestsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.adrequests.models.AdRequestsModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.features.adrequests.uistate.*
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AdRequestsViewModel(
    private val useCase: IAdRequestsUseCase
) : ViewModel() {

    private var currentParams: AdRequestsParams = AdRequestsParams()
    private var adRequestsModel: AdRequestsModel = AdRequestsModel()
    private var disposableBad = CompositeDisposable()

    private val dataSource: Single<AdRequestsModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val clearDbCall: Completable
        get() = Completable.create {
            try {
                useCase.clearDb()
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }

    private val observer = object : SingleObserver<AdRequestsModel> {

        override fun onSubscribe(d: Disposable) {
            disposableBad.add(d)
        }

        override fun onSuccess(model: AdRequestsModel) {
            adRequestsModel = model
            adRequestsUiMutableState.value = Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1
            )
        }

        override fun onError(e: Throwable) {
            adRequestsUiMutableState.value = Error(e)
        }
    }

    private val adRequestsUiMutableState = MutableLiveData<AdRequestsUiState>()

    val adRequestsData: LiveData<AdRequestsUiState>
        get() = adRequestsUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            adRequestsUiMutableState.value = Loading
            disposableBad.clear()
            loadModel()
        } else {
            adRequestsUiMutableState.value = Success(adRequestsModel)
        }
    }

    fun loadMore() {
        adRequestsUiMutableState.value = Pagination
        disposableBad.clear()
        loadModel()
    }

    fun refresh() {
        adRequestsModel = AdRequestsModel()
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER
        )

        disposableBad.clear()
        disposableBad.add(
            clearDbCall
                .async()
                .subscribe { loadModel() }
        )
    }

    private fun loadModel() {
        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposableBad.clear()
        clearDbCall.async().subscribe()
    }
}