package com.adcenter.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.async
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.features.details.uistate.DetailsUiState
import com.adcenter.features.details.usecase.IDetailsUseCase
import com.adcenter.datasource.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class DetailsViewModel(
    private val useCase: IDetailsUseCase
) : ViewModel() {

    private var currentParams: DetailsRequestParams = DetailsRequestParams()
    private var detailsModel: DetailsModel? = null
    private var disposable: Disposable? = null

    private val dataSource: Single<DetailsModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> {
                    detailsModel = result.value
                    it.onSuccess(result.value)
                }
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<DetailsModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: DetailsModel) {
            detailsUiMutableState.value = DetailsUiState.Success(model)
        }

        override fun onError(e: Throwable) {
            detailsUiMutableState.value = DetailsUiState.Error(e)
        }
    }

    private val detailsUiMutableState = MutableLiveData<DetailsUiState>()

    val detailsData: LiveData<DetailsUiState>
        get() = detailsUiMutableState

    fun load(detailsId: String) {
        val detailsCopy = detailsModel

        when {
            detailsId.isEmpty() -> detailsUiMutableState.value = DetailsUiState.Error(Throwable())
            detailsCopy != null -> detailsUiMutableState.value = DetailsUiState.Success(detailsCopy)
            else -> {
                detailsUiMutableState.value = DetailsUiState.Loading
                currentParams = DetailsRequestParams(detailsId)
                loadModel()
            }
        }
    }

    private fun loadModel() {
        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}