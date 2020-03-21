package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
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
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.utils.EmptyTextWatcher
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    init {
        Injector.appComponent.inject(this)
    }

    override val toolbarTitle: String
        get() = resourceProvider.searchTitle

    override val layout: Int = R.layout.fragment_search

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val viewModel by lazy {
        provideViewModel(SearchViewModel::class.java)
    }

    private lateinit var adapter: AdsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
        }

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            load()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        searchText.addTextChangedListener(textWatcher)

        viewModel.load()
    }

    override fun onDestroyView() {
        searchText.removeTextChangedListener(textWatcher)

        super.onDestroyView()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(::onItemClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SearchUiState.NewSearch -> {
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                    adapter.showPagination()
                }
                is SearchUiState.Pagination -> {
                    adapter.showPagination()
                }
                is SearchUiState.Success -> {
                    adapter.hidePagination()

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
                    adapter.hidePagination()

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

    private fun load() = viewModel.load(searchText.text.toString())

    private fun loadMore() = viewModel.loadMore()
}