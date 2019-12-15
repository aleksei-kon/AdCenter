package com.adcenter.features.newdetails.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.features.newdetails.uistate.NewDetailsUiState
import com.adcenter.features.newdetails.usecase.INewDetailsUseCase
import com.adcenter.features.newdetails.usecase.IUploadPhotoUseCase
import com.adcenter.utils.Constants.EMPTY
import com.adcenter.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class NewDetailsViewModel(
    private val context: Context,
    private val newDetailsUseCase: INewDetailsUseCase,
    private val uploadPhotoUseCase: IUploadPhotoUseCase,
    private val gson: Gson
) : ViewModel(), CoroutineScope {

    private var newDetailsModel: NewDetailsModel = NewDetailsModel()
    private var currentParams: NewDetailsRequestParams = NewDetailsRequestParams()
    private var photos = mutableListOf<Uri>()

    private val coroutineScopeJob = Job()
    private val newDetailsUiMutableState = MutableLiveData<NewDetailsUiState>()

    val newDetailsData: LiveData<NewDetailsUiState>
        get() = newDetailsUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun addPhoto(uri: Uri) {
        photos.add(uri)
    }

    fun upload(params: NewDetailsRequestParams) {
        newDetailsUiMutableState.value = NewDetailsUiState.WaitLoading
        currentParams = params
        uploadModel()
    }

    private fun uploadModel() {
        coroutineContext.cancelChildren()

        launch {
            val photoUrls: List<String> = photos.map { getFile(it) }.map {
                when (val result = uploadPhotoUseCase.upload(it)) {
                    is Result.Success -> result.value
                    is Result.Error -> EMPTY
                }
            }

            currentParams = currentParams.copy(photos = photoUrls)

            val json = gson.toJson(currentParams)

            when (val result = newDetailsUseCase.upload(json)) {
                is Result.Success -> {
                    newDetailsModel = result.value

                    newDetailsUiMutableState.value = NewDetailsUiState.Success(newDetailsModel)
                }
                is Result.Error -> {
                    newDetailsUiMutableState.value = NewDetailsUiState.Error(result.exception)
                }
            }
        }
    }

    private fun getFile(uri: Uri): File = File(getRealPathFromURI(uri))

    private fun getRealPathFromURI(contentUri: Uri): String {
        return context.contentResolver.query(
            contentUri,
            arrayOf(MediaStore.Images.Media.DATA),
            null, null, null
        )?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: EMPTY
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}