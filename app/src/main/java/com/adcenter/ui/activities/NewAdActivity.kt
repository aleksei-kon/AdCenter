package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.*
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.features.newdetails.uistate.NewDetailsUiState
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

        addPhotoButton.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        }

        addButton.setOnClickListener {
            upload()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            data?.data?.let {
                adapter.addItems(it)
                viewModel.addPhoto(it)
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initRecycler() {
        adapter = NewPhotosAdapter(this)
        photosRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photosRecycler.adapter = adapter
    }

    private fun upload() {
        val params = NewDetailsRequestParams(
            title = titleEditText.text.toString(),
            price = priceEditText.text.toString(),
            location = placeEditText.text.toString(),
            synopsis = synopsisEditText.text.toString(),
            phone = phoneEditText.text.toString()
        )

        viewModel.upload(params)
    }

    private fun setViewModelObserver() {
        viewModel.newDetailsData.observe(this, Observer {
            when (it) {
                is NewDetailsUiState.WaitLoading -> {
                    viewLayout.setChildsEnabled(false)
                    progressBar.visible()
                }
                is NewDetailsUiState.Success -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    finish()
                }
                is NewDetailsUiState.Error -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }
}