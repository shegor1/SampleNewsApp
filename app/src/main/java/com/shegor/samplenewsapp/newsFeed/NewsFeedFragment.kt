package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.InternetNewsListObserversSetting
import com.shegor.samplenewsapp.base.InternetNewsListViewModel
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.*
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedBinding
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsFilterCategory

class NewsFeedFragment :
    BaseRecyclerViewFragment<NewsFeedViewModel, NewsFeedViewModel.Factory, FragmentNewsFeedBinding, NewsRepo, NewsListAdapter>(),
    BottomMenuReselection, NewsRepoInstantiating, NewsRvAdapterInstantiating,
    InternetNewsListObserversSetting, NewsNavigation {

    companion object {
        const val ARG_OBJECT = "filterCategory"
    }

    private lateinit var category: NewsFilterCategory

    override fun getNavigator() = NewsFeedNavigator(viewModel)

    override fun getFragmentRepository() = getRepo()
    override fun getViewModelFactory(repo: NewsRepo): NewsFeedViewModel.Factory {
        category = arguments?.getParcelable(ARG_OBJECT) ?: NewsFilterCategory.GENERAL
        return NewsFeedViewModel.Factory(repo, category)
    }

    override fun getViewModel() = NewsFeedViewModel::class.java

    override val layoutId = R.layout.fragment_news_feed

    override fun getRvAdapter() = getNewsRvAdapter { newsItem, clickedViewId ->
        NewsClickListener().onClick(clickedViewId, newsItem, viewModel)
    }

    override fun getRecyclerView() = binding.newsRecyclerView

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

        viewModel.filterCountryLiveData.observe(viewLifecycleOwner) {
            val currentFilterCountryId = viewModel.filterCountry
            val newFilterCountryId = it.filterCountryStringId

            if (newFilterCountryId != currentFilterCountryId ?: -1) {
                viewModel.filterCountry = newFilterCountryId
                viewModel.getNewsData(category)
            }
        }

        viewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                NewsLoadingStatus.INIT_ERROR, NewsLoadingStatus.REFRESHING_ERROR -> {
                    Toast.makeText(
                        this.context,
                        getString(R.string.network_error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
            binding.newsRecyclerView
        )
        setInternetNewsObservers(viewModel, viewLifecycleOwner, recyclerViewAdapter)
        setNavigationObserver(this)
        setListeners()
    }

    private fun connectDataBinding() {
        binding.newsViewModel = viewModel
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getNewsData(category)
        }
    }
}
