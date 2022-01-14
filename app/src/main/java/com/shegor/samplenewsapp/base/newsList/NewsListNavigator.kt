package com.shegor.samplenewsapp.base.newsList

import androidx.fragment.app.Fragment
import com.shegor.samplenewsapp.base.BaseNavigator
import com.shegor.samplenewsapp.models.NewsModel

abstract class NewsListNavigator(protected open val viewModel: NewsListViewModel) : BaseNavigator(){

    fun setNavToDetailsFragmentObserver(fragment: Fragment, navigate: (newsItem: NewsModel) -> Unit) {

            viewModel.navigationToDetailsFragment.observe(fragment.viewLifecycleOwner, { newsItem ->
                newsItem?.let {
                    navigate(it)
                    viewModel.finishNavigationToDetailsFragment()
                }
            })
        }
    }
