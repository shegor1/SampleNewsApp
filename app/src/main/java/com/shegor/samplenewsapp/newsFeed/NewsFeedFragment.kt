package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import android.view.View
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.networkNews.BaseNetworkNewsListFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedBinding
import com.shegor.samplenewsapp.service.NewsFilterCategory
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

class NewsFeedFragment :
    BaseNetworkNewsListFragment<NewsFeedViewModel, FragmentNewsFeedBinding, NewsFeedNavigator>() {

    companion object {
        const val ARG_OBJECT = "filterCategory"
    }

    override val layoutId = R.layout.fragment_news_feed

    override fun getViewModel() = NewsFeedViewModel::class.java

    override fun getViewModelFactory(): BaseViewModelFactory<NewsFeedViewModel> =
        BaseViewModelFactory(NewsFeedViewModel::class.java) {
            NewsFeedViewModel(
                newsRepo,
                arguments?.getParcelable(ARG_OBJECT) ?: NewsFilterCategory.GENERAL,
                coordinator::navigateToDetailsFragment
            )
        }

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun getNavigator() = NewsFeedNavigator(requireActivity())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshNewsListIfNetworkError()
        setListeners()
    }

    override fun connectDataBinding() {
        super.connectDataBinding()
        binding.newsViewModel = viewModel
    }

    private fun refreshNewsListIfNetworkError(){
        if (
            viewModel.status.value == NewsLoadingStatus.INIT_ERROR ||
            viewModel.status.value == NewsLoadingStatus.REFRESHING_ERROR
        ) viewModel.getLatestNewsData()
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLatestNewsData()
        }
    }
}