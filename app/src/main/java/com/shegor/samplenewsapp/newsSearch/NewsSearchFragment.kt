package com.shegor.samplenewsapp.newsSearch

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.networkNews.BaseNetworkNewsListFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsSearchBinding
import com.shegor.samplenewsapp.utils.hideKeyboard

class NewsSearchFragment :
    BaseNetworkNewsListFragment<NewsSearchViewModel, FragmentNewsSearchBinding, NewsSearchNavigator>() {

    override fun getViewModel() = NewsSearchViewModel::class.java

    override val layoutId = R.layout.fragment_news_search
    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsSearchViewModel::class.java) {
            NewsSearchViewModel(newsRepo, coordinator::navigateToDetailsFragment)
        }

    override fun getRecyclerView() = binding.searchRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setObservers()
        setupListeners()
    }

    private fun setObservers() {

        viewModel.clearSearchBarAction.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                binding.searchBar.text?.clear()
                viewModel.finishClearingSearchBar()
            }
        })
    }


    private fun connectDataBinding() {
        binding.searchViewModel = viewModel
    }

    private fun setupListeners() {

        binding.searchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.isNotBlank()) {


                    viewModel.getNewsDataByQuery(v.text.toString())

                } else {
                    Toast.makeText(
                        this.context,
                        getString(R.string.empty_search_field),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                this.hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.deleteQueryButton.setOnClickListener {
            viewModel.clearSearchBar()
        }
    }

    override fun getNavigator() = NewsSearchNavigator(this)


}