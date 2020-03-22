package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdsAdapter
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

class MyAdsFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    init {
        Injector.appComponent.inject(this)
    }

    override val toolbarTitle: String
        get() = resourceProvider.myAdsTitle

    override val layout: Int = R.layout.layout_recycler

    private val viewModel by lazy {
        provideViewModel(MyAdsViewModel::class.java)
    }

    private lateinit var adapter: AdsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        initViews()
        load()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(::onItemClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun initViews() {
        swipeRefresh.setOnRefreshListener {
            refresh()
        }
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
                    adapter.showPagination()
                }
                is MyAdsUiState.Success -> {
                    swipeRefresh.isRefreshing = false
                    adapter.hidePagination()
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
                    adapter.hidePagination()
                    progressBar.gone()

                    if (adapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    it.throwable.message?.let { message -> requireContext().longToast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        adapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(programsScrollListener)
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