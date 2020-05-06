package com.adcenter.ui.activities

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.EmptyPageException
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.*
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.search.uistate.*
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.ui.adapters.ItemType
import com.adcenter.ui.adapters.ItemType.LINEAR
import com.adcenter.ui.adapters.ViewHolderType.ITEM
import com.adcenter.ui.adapters.ViewHolderType.PAGINATION
import com.adcenter.ui.common.RecyclerViewMargin
import com.adcenter.ui.common.ScrollToEndListener
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.noDataMessage
import kotlinx.android.synthetic.main.activity_search.recyclerView
import kotlinx.android.synthetic.main.layout_recycler.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SINGLE_COUNT = 1
private const val LANDSCAPE_COUNT = 4

class SearchActivity : OfflineActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var recyclerAdapter: AdsAdapter

    private val viewModel by lazy {
        provideViewModel(
            SearchViewModel::class.java,
            viewModelFactory
        )
    }

    private val recyclerScrollListener =
        ScrollToEndListener { loadMore() }

    private val disposables = CompositeDisposable()

    override val layout: Int = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .searchComponent()
            .inject(this)

        backButton.setOnClickListener { finish() }
        initRecycler()
        setViewModelObserver()
        load()

        disposables.add(
            searchText.textChanges()
                .skipInitialValue()
                .debounce(750, TimeUnit.MILLISECONDS)
                .filter { it.length > 1 }
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { load(it) }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.forceUpdate()
    }

    override fun onDestroy() {
        disposables.clear()

        super.onDestroy()
    }

    override fun updateContentVisibility() {
        if (isConnectedToNetwork()) {
            offlineMessage.gone()
            content.visible()
        } else {
            content.gone()
            offlineMessage.visible()
        }
    }

    private fun initRecycler() {
        recyclerView.apply {
            when (resources.configuration.orientation) {
                ORIENTATION_LANDSCAPE -> {
                    addItemDecoration(
                        RecyclerViewMargin(
                            margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
                            columns = LANDSCAPE_COUNT
                        )
                    )
                    layoutManager = GridLayoutManager(this@SearchActivity, LANDSCAPE_COUNT).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int =
                                when (recyclerAdapter.getItemViewType(position)) {
                                    ITEM.ordinal -> SINGLE_COUNT
                                    PAGINATION.ordinal -> LANDSCAPE_COUNT
                                    else -> LANDSCAPE_COUNT
                                }
                        }
                    }
                    recyclerAdapter = AdsAdapter(ItemType.GRID, ::onItemClick)
                }
                ORIENTATION_PORTRAIT -> {
                    addItemDecoration(
                        RecyclerViewMargin(
                            margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
                            columns = SINGLE_COUNT
                        )
                    )
                    layoutManager = LinearLayoutManager(this@SearchActivity)
                    recyclerAdapter = AdsAdapter(LINEAR, ::onItemClick)
                }
            }

            adapter = recyclerAdapter
        }

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(this, Observer {
            when (it) {
                is NewSearch -> {
                    removeScrollListener()
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                    recyclerAdapter.showPagination()
                }
                is Pagination -> {
                    removeScrollListener()
                    recyclerAdapter.showPagination()
                }
                is Updating -> {
                    removeScrollListener()
                }
                is Success -> {
                    recyclerAdapter.hidePagination()

                    if (it.result.ads.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                        recyclerView.visible()
                    }

                    setRecyclerItems(it.result.ads)
                    setScrollListener()
                }
                is Error -> {
                    recyclerAdapter.hidePagination()

                    if (recyclerAdapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    if (it.throwable is EmptyPageException) {
                        Log.d(Constants.APP_LOG_NAME, it.throwable.message ?: "Empty page")
                    } else {
                        it.throwable.message?.let { message -> longToast(message) }
                    }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(recyclerScrollListener)
    }

    private fun removeScrollListener() {
        recyclerView.clearOnScrollListeners()
    }

    private fun onItemClick(id: Int) {
        startActivityForResult(
            Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            },
            123
        )
    }

    private fun load() = viewModel.load()

    private fun load(searchText: String) = viewModel.load(searchText)

    private fun loadMore() = viewModel.loadMore()
}