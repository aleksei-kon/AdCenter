package com.adcenter.ui.fragments

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.provideViewModel
import com.adcenter.extensions.visible
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.myads.uistate.MyAdsUiState
import com.adcenter.features.myads.viewmodel.MyAdsViewModel
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.common.IPageConfiguration
import com.adcenter.ui.common.RecyclerViewMargin
import com.adcenter.ui.common.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.ui.adapters.ItemType.GRID
import com.adcenter.ui.adapters.ViewHolderType.ITEM
import com.adcenter.ui.adapters.ViewHolderType.PAGINATION
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

private const val SINGLE_COUNT = 1
private const val PORTRAIT_COUNT = 2
private const val LANDSCAPE_COUNT = 4

class MyAdsFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        provideViewModel(
            MyAdsViewModel::class.java,
            viewModelFactory
        )
    }

    private val recyclerAdapter = AdsAdapter(GRID, ::onItemClick)

    private val recyclersScrollListener =
        ScrollToEndListener { loadMore() }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String
        get() = resourceProvider.myAdsTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Injector
            .appComponent
            .myAdsComponent()
            .inject(this)

        initRecycler()
        initЫwipeRefresh()
        setViewModelObserver()
        load()
    }

    private fun initRecycler() {
        val columns = when (resources.configuration.orientation) {
            ORIENTATION_LANDSCAPE -> LANDSCAPE_COUNT
            ORIENTATION_PORTRAIT -> PORTRAIT_COUNT
            else -> SINGLE_COUNT
        }

        val itemDecoration = RecyclerViewMargin(
            margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
            columns = columns
        )

        val gridLayoutManager = GridLayoutManager(requireContext(), columns).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (recyclerAdapter.getItemViewType(position)) {
                        ITEM.ordinal -> SINGLE_COUNT
                        PAGINATION.ordinal -> columns
                        else -> columns
                    }
            }
        }

        recyclerView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = gridLayoutManager
            adapter = recyclerAdapter
        }

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.myAdsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MyAdsUiState.Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is MyAdsUiState.Pagination -> {
                    recyclerAdapter.showPagination()
                }
                is MyAdsUiState.Success -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
                    progressBar.gone()

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
                is MyAdsUiState.Error -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
                    progressBar.gone()

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

    private fun initЫwipeRefresh() {
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(recyclersScrollListener)
    }

    private fun onItemClick(id: String) {
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