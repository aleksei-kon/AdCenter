package com.adcenter.ui.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.features.search.SearchConstants.SEARCH_SCOPE_ID
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.utils.EmptyTextWatcher
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SearchFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.search_title) }

    override val layout: Int = R.layout.fragment_search

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val fragmentScope =
        getKoin().getOrCreateScope(SEARCH_SCOPE_ID, named<SearchFragment>())

    private val viewModel: SearchViewModel = fragmentScope.get()

    private lateinit var adapter: AdsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
            deleteScrollListener()
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
    }

    override fun onDestroyView() {
        searchText.removeTextChangedListener(textWatcher)

        super.onDestroyView()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SearchUiState.NewSearch -> {
                    deleteScrollListener()
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                }
                is SearchUiState.Success -> {
                    if (it.result.ads.isEmpty()) {
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                    }

                    recyclerView.visible()
                    setRecyclerItems(it.result.ads)
                    setScrollListener()
                }
                is SearchUiState.Error -> {
                    noDataMessage.visible()
                    recyclerView.gone()
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        adapter.setItems(items)
    }

    private fun deleteScrollListener() {
        recyclerView.clearOnScrollListeners()
    }

    private fun setScrollListener() {
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(programsScrollListener)
    }

    private fun load() = viewModel.load(searchText.text.toString())

    private fun loadMore() = viewModel.loadMore()
}