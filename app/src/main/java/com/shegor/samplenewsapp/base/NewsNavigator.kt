package com.shegor.samplenewsapp.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.newsFeed.NewsFeedTabsFragmentDirections

abstract class NewsNavigator(protected open val viewModel: NewsViewModel) {

    fun setNavigationObserver(fragment: Fragment, navigate: (newsItem: NewsModel) -> Unit) {

            viewModel.navigationToDetailsFragment.observe(fragment.viewLifecycleOwner, { newsItem ->
                newsItem?.let {
                    navigate(it)
                    viewModel.finishNavigationToDetailsFragment()
                }
            })
        }
    abstract fun setupNavigation(fragment: Fragment)
    }
