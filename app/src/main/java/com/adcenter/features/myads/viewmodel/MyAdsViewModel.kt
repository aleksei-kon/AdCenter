package com.adcenter.features.myads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.extensions.async
import com.adcenter.features.myads.MyAdsConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.myads.models.MyAdsModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.features.myads.uistate.MyAdsUiState
import com.adcenter.features.myads.usecase.IMyAdsUseCase
import com.adcenter.entities.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MyAdsViewModel(
    private val useCase: IMyAdsUseCase
) : ViewModel() {

    private var currentParams: MyAdsRequestParams = MyAdsRequestParams()
    private var myAdsModel: MyAdsModel = MyAdsModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<MyAdsModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(updateModel(result.value))
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<MyAdsModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: MyAdsModel) {
            myAdsUiMutableState.value = MyAdsUiState.Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1
            )
        }

        override fun onError(e: Throwable) {
            myAdsUiMutableState.value = MyAdsUiState.Error(e)
        }
    }

    private val myAdsUiMutableState = MutableLiveData<MyAdsUiState>()

    val myAdsData: LiveData<MyAdsUiState>
        get() = myAdsUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            myAdsUiMutableState.value = MyAdsUiState.Loading
            loadModel()
        } else {
            myAdsUiMutableState.value = MyAdsUiState.Success(myAdsModel)
        }
    }

    fun loadMore() {
        myAdsUiMutableState.value = MyAdsUiState.Pagination
        loadModel()
    }

    fun refresh() {
        myAdsModel = MyAdsModel()
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
        newResponse: MyAdsModel
    ): MyAdsModel {
        myAdsModel = MyAdsModel(myAdsModel.ads + newResponse.ads)

        return myAdsModel
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}