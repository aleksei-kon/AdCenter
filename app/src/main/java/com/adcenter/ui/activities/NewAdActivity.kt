package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.extensions.toast
import com.adcenter.features.newdetails.NewDetailsConstants.NEW_DETAILS_SCOPE_ID
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.features.newdetails.uistate.NewDetailsUiState
import com.adcenter.features.newdetails.viewmodel.NewDetailsViewModel
import com.adcenter.ui.adapters.NewPhotosAdapter
import kotlinx.android.synthetic.main.activity_new_ad.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class NewAdActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_new_ad

    private lateinit var adapter: NewPhotosAdapter

    private val activityScope =
        getKoin().getOrCreateScope(NEW_DETAILS_SCOPE_ID, named<NewAdActivity>())

    private val viewModel: NewDetailsViewModel = activityScope.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    addPhotoButton.isEnabled = false
                    addButton.isEnabled = false
                }
                is NewDetailsUiState.Success -> {
                    finish()
                }
                is NewDetailsUiState.Error -> {
                    addPhotoButton.isEnabled = true
                    addButton.isEnabled = true
                    it.throwable.message?.let { message -> toast(message) }
                }
            }
        })
    }
}