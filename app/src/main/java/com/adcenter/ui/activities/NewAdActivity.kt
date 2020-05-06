package com.adcenter.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.network.NewDetailsModel
import com.adcenter.extensions.*
import com.adcenter.extensions.Constants.CURRENCY_LIST
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.LOCATION_MIN_LENGTH
import com.adcenter.extensions.Constants.SYNOPSIS_MIN_LENGTH
import com.adcenter.extensions.Constants.TITLE_LENGTH_RANGE
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.features.newdetails.uistate.Error
import com.adcenter.features.newdetails.uistate.Success
import com.adcenter.features.newdetails.uistate.UpdatePhotos
import com.adcenter.features.newdetails.uistate.WaitLoading
import com.adcenter.features.newdetails.viewmodel.NewDetailsViewModel
import com.adcenter.ui.adapters.NewPhotosAdapter
import kotlinx.android.synthetic.main.activity_new_ad.*
import javax.inject.Inject

class NewAdActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: NewPhotosAdapter

    private val viewModel by lazy {
        provideViewModel(
            NewDetailsViewModel::class.java,
            viewModelFactory
        )
    }

    override val layout: Int = R.layout.activity_new_ad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .newDetailsComponent()
            .inject(this)

        initToolbar()
        setViewModelObserver()
        initRecycler()
        initSpinner()

        addPhotoButton.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        }

        addButton.setOnClickListener {
            if (checkInput()) {
                upload()
            }
        }

        viewModel.updatePhotos()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            data?.data?.let {
                viewModel.addPhoto(it)
            }
        }
    }

    private fun initSpinner() {
        currencySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            CURRENCY_LIST
        )
    }

    private fun deletePhoto(uri: Uri) {
        viewModel.removePhoto(uri)
    }

    private fun updateAddPhotoButton() {
        if (adapter.itemCount > 10) {
            addPhotoButton.gone()
        } else {
            addPhotoButton.visible()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initRecycler() {
        adapter = NewPhotosAdapter(::deletePhoto)
        photosRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photosRecycler.adapter = adapter
    }

    private fun checkInput(): Boolean {
        val title = titleEditText.text.toString().trim()
        val price = priceEditText.text.toString().trim()
        val location = placeEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val synopsis = synopsisEditText.text.toString().trim()

        val message = when {
            title.isEmpty() -> resources.getString(R.string.empty_title_message)
            price.isEmpty() -> resources.getString(R.string.empty_price_message)
            location.isEmpty() -> resources.getString(R.string.empty_location_message)
            phone.isEmpty() -> resources.getString(R.string.empty_phone_message)
            synopsis.isEmpty() -> resources.getString(R.string.empty_synopsis_message)
            title.length !in TITLE_LENGTH_RANGE -> resources.getString(R.string.title_length_message)
            location.length < LOCATION_MIN_LENGTH -> resources.getString(R.string.location_length_message)
            synopsis.length < SYNOPSIS_MIN_LENGTH -> resources.getString(R.string.synopsis_length_message)
            else -> EMPTY
        }

        return if (message.isEmpty()) {
            true
        } else {
            longToast(message)

            false
        }
    }

    private fun upload() {
        val params = NewDetailsRequestParams(
            newDetailsModel = NewDetailsModel(
                title = titleEditText.text.toString().trim(),
                price = "${priceEditText.text.toString().trim()} ${currencySpinner.selectedItem}",
                location = placeEditText.text.toString().trim(),
                synopsis = synopsisEditText.text.toString().trim(),
                phone = phoneEditText.text.toString().trim()
            )
        )

        viewModel.upload(params)
    }

    private fun setViewModelObserver() {
        viewModel.newDetailsData.observe(this, Observer {
            when (it) {
                is UpdatePhotos -> {
                    adapter.setItems(it.list)
                    updateAddPhotoButton()
                }
                is WaitLoading -> {
                    viewLayout.setChildsEnabled(false)
                    progressBar.visible()
                }
                is Success -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    finish()
                }
                is Error -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }
}