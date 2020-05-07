package com.adcenter.ui.activities

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.adcenter.R
import com.adcenter.appconfig.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.*
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.EMPTY_ID
import com.adcenter.features.details.DetailsConstants.DETAILS_ID_KEY
import com.adcenter.features.details.DetailsConstants.IS_EDIT_PAGE
import com.adcenter.features.details.uistate.*
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.adapters.DetailsPhotosAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_ad_details_info.*
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int = R.layout.activity_details

    private val viewModel by lazy {
        provideViewModel(
            DetailsViewModel::class.java,
            viewModelFactory
        )
    }

    private val circularProgressDrawable by lazy {
        CircularProgressDrawable(this).apply {
            setColorSchemeColors(colorFromAttr(R.attr.buttonOutlineTextColor))
            strokeWidth = resources.getDimension(R.dimen.progress_button_progress_stroke_width)
            centerRadius = resources.getDimension(R.dimen.progress_button_progress_radius)
        }
    }

    private val detailsId: Int
        get() = intent.getIntExtra(DETAILS_ID_KEY, EMPTY_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .detailsComponent()
            .inject(this)

        window.apply {
            clearFlags(FLAG_TRANSLUCENT_STATUS)
            addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = TRANSPARENT
        }

        backButton.setOnClickListener { finish() }

        setViewModelObserver()

        if (intent.hasExtra(DETAILS_ID_KEY)) {
            load(detailsId)
        } else {
            noDataMessage.visible()
        }

        if (intent.hasExtra(IS_EDIT_PAGE) && intent.getBooleanExtra(IS_EDIT_PAGE, false)) {
            editButton.visible()
            deleteButton.visible()
        } else {
            editButton.gone()
            deleteButton.gone()
        }

        if (appConfig.isLoggedIn || appConfig.isAdmin) {
            addRemoveBookmarkButton.visible()
        } else {
            addRemoveBookmarkButton.gone()
        }

        if (appConfig.isAdmin) {
            showHideButton.visible()
        } else {
            showHideButton.gone()
        }

        initButtons()
    }

    private fun initButtons() {
        showHideButton.setOnClickListener {
            viewModel.makeAction(ShowHideIntent(detailsId))
        }
        addRemoveBookmarkButton.setOnClickListener {
            viewModel.makeAction(AddDeleteBookmarksIntent(detailsId))
        }
        editButton.setOnClickListener {

        }
        deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(this@DetailsActivity)
                .setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_dialog_message))
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> }
                .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                    viewModel.makeAction(DeleteIntent(detailsId))
                }
                .show()

        }
    }

    private fun enableActions() {
        showHideButton.isEnabled = true
        addRemoveBookmarkButton.isEnabled = true
        editButton.isEnabled = true
        deleteButton.isEnabled = true
    }

    private fun disableActions() {
        showHideButton.isEnabled = false
        addRemoveBookmarkButton.isEnabled = false
        editButton.isEnabled = false
        deleteButton.isEnabled = false
    }

    private fun hideActionsProgress() {
        showHideButton.icon = null
        addRemoveBookmarkButton.icon = null
        editButton.icon = null
        deleteButton.icon = null
        circularProgressDrawable.stop()
    }

    private fun setViewModelObserver() {
        viewModel.actionsData.observe(this, Observer {
            when (it) {
                is ShowHideProgress -> {
                    disableActions()
                    showHideButton.icon = circularProgressDrawable
                    circularProgressDrawable.start()
                }
                is AddDeleteBookmarksProgress -> {
                    disableActions()
                    addRemoveBookmarkButton.icon = circularProgressDrawable
                    circularProgressDrawable.start()
                }
                is DeleteProgress -> {
                    disableActions()
                    deleteButton.icon = circularProgressDrawable
                    circularProgressDrawable.start()
                }
                is ActionSuccess -> {
                    update(detailsId)
                }
                is ActionError -> {
                    hideActionsProgress()
                    enableActions()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })

        viewModel.detailsData.observe(this, Observer {
            when (it) {
                is Loading -> {
                    progressBar.visible()
                    content.gone()
                    noDataMessage.gone()
                }
                is Success -> {
                    progressBar.gone()
                    noDataMessage.gone()
                    content.visible()
                    bindModel(it.result)

                    hideActionsProgress()
                    enableActions()
                }
                is Error -> {
                    progressBar.gone()
                    content.gone()
                    noDataMessage.visible()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }

    private fun bindModel(model: DetailsModel) {
        setRecyclerItems(model.photos)
        itemTitle.setTextWithVisibility(model.title)
        price.setTextWithVisibility(model.price)
        location.setTextWithVisibility(model.location)
        date.setTextWithVisibility(model.date)
        views.setTextWithVisibility(model.views)
        synopsis.setTextWithVisibility(model.synopsis)
        username.setTextWithVisibility(model.username)
        phone.setTextWithVisibility(model.phone)

        addRemoveBookmarkButton.text = if (model.isBookmark) {
            getString(R.string.delete_from_bookmarks)
        } else {
            getString(R.string.add_to_bookmark)
        }

        showHideButton.text = if (model.isShown) {
            getString(R.string.hide_advert)
        } else {
            getString(R.string.show_advert)
        }
    }

    private fun setRecyclerItems(items: List<String>) {
        val currentAdapter = postPhotosSlider.sliderAdapter

        when {
            currentAdapter is DetailsPhotosAdapter && currentAdapter.getPhotosList() == items -> return
            else -> {
                val adapter = DetailsPhotosAdapter(this)
                postPhotosSlider.sliderAdapter = adapter

                if (items.isEmpty()) {
                    adapter.setPhotos(listOf(EMPTY))
                } else {
                    adapter.setPhotos(items)
                }
            }
        }
    }

    private fun load(detailsId: Int) = viewModel.load(detailsId)

    private fun update(detailsId: Int) = viewModel.update(detailsId)
}