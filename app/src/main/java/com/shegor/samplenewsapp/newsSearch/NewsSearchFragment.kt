package com.shegor.samplenewsapp.newsSearch

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.*
import com.shegor.samplenewsapp.databinding.FragmentNewsSearchBinding
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsSearchFragment :
    BaseRecyclerViewFragment<NewsSearchViewModel, FragmentNewsSearchBinding, NewsRepo, NewsListAdapter>(),
    BottomMenuReselection, NewsRepoInstantiating, NewsRvAdapterInstantiating,
    InternetNewsListObserversSetting, NewsNavigation {

    override fun getNavigator() = NewsSearchNavigator(viewModel)


    override fun getViewModel() = NewsSearchViewModel::class.java

    override val layoutId = R.layout.fragment_news_search
    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsSearchViewModel::class.java) { NewsSearchViewModel(getRepo()) }

    override fun getRvAdapter() = getNewsRvAdapter(viewModel)

    override fun getRecyclerView() = binding.searchRecyclerView

    override fun setInternetNewsObservers(
        internetNewsListViewModel: InternetNewsListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: NewsListAdapter
    ) {
        super.setInternetNewsObservers(
            internetNewsListViewModel,
            viewLifecycleOwner,
            recyclerViewAdapter
        )

        viewModel.clearSearchBarAction.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                binding.searchBar.text?.clear()
                viewModel.finishClearingSearchBar()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setItemReselectedListener(
            requireActivity(),
            lifecycleScope,
            findNavController(),
            binding.searchRecyclerView
        )

        setInternetNewsObservers(viewModel, viewLifecycleOwner, recyclerViewAdapter)
        setNavigationObserver(this)
        setupListeners()
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


}