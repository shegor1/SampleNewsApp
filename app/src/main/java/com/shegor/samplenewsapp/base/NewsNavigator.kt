package com.shegor.samplenewsapp.base

import androidx.fragment.app.Fragment
import com.shegor.samplenewsapp.models.NewsModel

abstract class NewsNavigator(protected open val viewModel: NewsListViewModel) {

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
