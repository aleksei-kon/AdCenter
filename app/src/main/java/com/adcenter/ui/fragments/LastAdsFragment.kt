package com.adcenter.ui.fragments

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.provideViewModel
import com.adcenter.extensions.visible
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.lastads.uistate.Error
import com.adcenter.features.lastads.uistate.LastAdsUiState
import com.adcenter.features.lastads.uistate.Loading
import com.adcenter.features.lastads.uistate.Pagination
import com.adcenter.features.lastads.uistate.Success
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.common.IPageConfiguration
import com.adcenter.ui.common.RecyclerViewMargin
import com.adcenter.ui.common.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.ui.adapters.ItemType.GRID
import com.adcenter.ui.adapters.ItemType.LINEAR
import com.adcenter.ui.adapters.ViewHolderType.ITEM
import com.adcenter.ui.adapters.ViewHolderType.PAGINATION
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

private const val SINGLE_COUNT = 1
private const val LANDSCAPE_COUNT = 4

class LastAdsFragment : BaseFragment(),
    IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var recyclerAdapter: AdsAdapter

    private val viewModel by lazy {
        provideViewModel(
            LastAdsViewModel::class.java,
            viewModelFactory
        )
    }

    private val recyclerScrollListener =
        ScrollToEndListener { loadMore() }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String
        get() = resourceProvider.lastAdsTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Injector
            .appComponent
            .lastAdsComponent()
            .inject(this)

        initRecycler()
        initSwipeRefresh()
        setViewModelObserver()
        load()
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
                    layoutManager = GridLayoutManager(requireContext(), LANDSCAPE_COUNT).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int =
                                when (recyclerAdapter.getItemViewType(position)) {
                                    ITEM.ordinal -> SINGLE_COUNT
                                    PAGINATION.ordinal -> LANDSCAPE_COUNT
                                    else -> LANDSCAPE_COUNT
                                }
                        }
                    }
                    recyclerAdapter = AdsAdapter(GRID, ::onItemClick)
                }
                ORIENTATION_PORTRAIT -> {
                    addItemDecoration(
                        RecyclerViewMargin(
                            margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
                            columns = SINGLE_COUNT
                        )
                    )
                    layoutManager = LinearLayoutManager(requireContext())
                    recyclerAdapter = AdsAdapter(LINEAR, ::onItemClick)
                }
            }

            adapter = recyclerAdapter
        }

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.lastAdsData.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is Pagination -> {
                    recyclerAdapter.showPagination()
                }
                is Success -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
                    progressBar.gone()

                    if (uiState.result.ads.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                        recyclerView.visible()
                    }

                    setRecyclerItems(uiState.result.ads)
                    setScrollListener()
                }
                is Error -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
                    progressBar.gone()

                    if (recyclerAdapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    uiState.throwable.message?.let { message -> longToast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(recyclerScrollListener)
    }

    private fun onItemClick(id: Int) {
        context?.startActivity(
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            }
        )
    }

    private fun load() = viewModel.load()

    private fun loadMore() = viewModel.loadMore()

    private fun refresh() = viewModel.refresh()
}