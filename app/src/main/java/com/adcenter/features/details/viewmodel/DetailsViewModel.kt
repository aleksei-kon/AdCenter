package com.adcenter.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.features.details.uistate.DetailsUiState
import com.adcenter.features.details.usecase.IDetailsUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsViewModel(private val detailsUseCase: IDetailsUseCase) : ViewModel(), CoroutineScope {

    private var currentParams: DetailsRequestParams = DetailsRequestParams()

    private val coroutineScopeJob = Job()
    private val detailsUiMutableState = MutableLiveData<DetailsUiState>()

    val detailsData: LiveData<DetailsUiState>
        get() = detailsUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load(detailsId: String) {
        if (detailsId.isEmpty()) {
            detailsUiMutableState.value = DetailsUiState.Error(Throwable())
        } else {
            detailsUiMutableState.value = DetailsUiState.Loading
            currentParams = DetailsRequestParams(detailsId)
            loadModel()
        }
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        launch {
            when (val result = detailsUseCase.load(currentParams)) {
                is Result.Success -> {
                    detailsUiMutableState.value = DetailsUiState.Success(result.value)
                }
                is Result.Error -> {
                    detailsUiMutableState.value = DetailsUiState.Error(result.exception)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}