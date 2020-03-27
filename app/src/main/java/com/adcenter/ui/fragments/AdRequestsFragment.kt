package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.provideViewModel
import com.adcenter.extensions.visible
import com.adcenter.features.adrequests.uistate.AdRequestsUiState
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import com.adcenter.features.details.DetailsConstants
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.RecyclerViewMargin
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdRequestsAdapter
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

class AdRequestsFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    init {
        Injector.appComponent.inject(this)
    }

    private val recyclerAdapter = AdRequestsAdapter(::onItemClick)

    private val viewModel by lazy { provideViewModel(AdRequestsViewModel::class.java) }

    private val recyclerScrollListener = ScrollToEndListener { loadMore() }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String
        get() = resourceProvider.adRequestsTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initSwipeRefresh()
        setViewModelObserver()
        load()
    }

    private fun initRecycler() {
        recyclerView.apply {
            addItemDecoration(
                RecyclerViewMargin(
                    margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
                    columns = 1
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.adRequestsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AdRequestsUiState.Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is AdRequestsUiState.Pagination -> {
                    recyclerAdapter.showPagination()
                }
                is AdRequestsUiState.Success -> {
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
                is AdRequestsUiState.Error -> {
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

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(recyclerScrollListener)
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