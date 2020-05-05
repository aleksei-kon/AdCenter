package com.adcenter.features.myads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.myads.MyAdsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.myads.models.MyAdsModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.features.myads.uistate.*
import com.adcenter.features.myads.usecase.IMyAdsUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MyAdsViewModel(
    private val useCase: IMyAdsUseCase
) : ViewModel() {

    private var currentParams: MyAdsRequestParams = MyAdsRequestParams()
    private var myAdsModel: MyAdsModel = MyAdsModel()
    private var disposableBad = CompositeDisposable()

    private val dataSource: Single<MyAdsModel>
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

    private val observer = object : SingleObserver<MyAdsModel> {

        override fun onSubscribe(d: Disposable) {
            disposableBad.add(d)
        }

        override fun onSuccess(model: MyAdsModel) {
            myAdsModel = model
            myAdsUiMutableState.value = Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1,
                isForceRefresh = false
            )
        }

        override fun onError(e: Throwable) {
            myAdsUiMutableState.value = Error(e)
        }
    }

    private val myAdsUiMutableState = MutableLiveData<MyAdsUiState>()

    val myAdsData: LiveData<MyAdsUiState>
        get() = myAdsUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            myAdsUiMutableState.value = Loading
            disposableBad.clear()
            loadModel()
        } else {
            myAdsUiMutableState.value = Success(myAdsModel)
        }
    }

    fun loadMore() {
        myAdsUiMutableState.value = Pagination
        disposableBad.clear()
        loadModel()
    }

    fun forceUpdate() {
        disposableBad.clear()
        currentParams = currentParams.copy(
            pageNumber = currentParams.pageNumber - 1,
            isForceRefresh = true
        )
        loadModel()
    }

    fun refresh() {
        disposableBad.clear()
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER,
            isForceRefresh = true
        )
        loadModel()
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