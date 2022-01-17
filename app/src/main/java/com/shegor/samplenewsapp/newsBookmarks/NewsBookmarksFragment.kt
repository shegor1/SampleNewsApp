package com.shegor.samplenewsapp.newsBookmarks

import android.os.Bundle
import android.view.View
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.newsList.BaseNewsListFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsBookmarksBinding

class NewsBookmarksFragment :
    BaseNewsListFragment<NewsBookmarksViewModel, FragmentNewsBookmarksBinding, NewsBookmarksNavigator>() {


    override fun getViewModel() = NewsBookmarksViewModel::class.java

    override val layoutId = R.layout.fragment_news_bookmarks

    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsBookmarksViewModel::class.java) {
            NewsBookmarksViewModel(newsRepo, coordinator::navigateToDetailsFragment)
        }

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
    }

    private fun connectDataBinding() {
        binding.newsBookmarksViewModel = viewModel
    }

    override fun getNavigator() = NewsBookmarksNavigator(this)

}