package com.adcenter.ui.activities

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.*
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.common.RecyclerViewMargin
import com.adcenter.ui.common.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.ui.adapters.ItemType
import com.adcenter.ui.adapters.ItemType.LINEAR
import com.adcenter.ui.adapters.ViewHolderType.ITEM
import com.adcenter.ui.adapters.ViewHolderType.PAGINATION
import com.adcenter.ui.common.EmptyTextWatcher
import kotlinx.android.synthetic.main.activity_search.*
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

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            load(searchText.text.toString())
        }
    }

    override val layout: Int = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .searchComponent()
            .inject(this)

        backButton.setOnClickListener { finish() }

        initRecycler()
        searchText.addTextChangedListener(textWatcher)
        setViewModelObserver()
        load()
    }

    override fun onDestroy() {
        searchText.removeTextChangedListener(textWatcher)

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
                is SearchUiState.NewSearch -> {
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                    recyclerAdapter.showPagination()
                }
                is SearchUiState.Pagination -> {
                    recyclerAdapter.showPagination()
                }
                is SearchUiState.Success -> {
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
                is SearchUiState.Error -> {
                    recyclerAdapter.hidePagination()

                    if (recyclerAdapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    it.throwable.message?.let { message -> longToast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(recyclerScrollListener)
    }

    private fun onItemClick(id: String) {
        startActivity(
            Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            }
        )
    }

    private fun load() = viewModel.load()

    private fun load(searchText: String) = viewModel.load(searchText)

    private fun loadMore() = viewModel.loadMore()
}