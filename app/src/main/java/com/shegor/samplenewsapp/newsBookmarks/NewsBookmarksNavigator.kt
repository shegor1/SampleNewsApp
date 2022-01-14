package com.shegor.samplenewsapp.newsBookmarks

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.newsList.NewsListNavigator
import com.shegor.samplenewsapp.base.newsList.NewsListViewModel

class NewsBookmarksNavigator(viewModel: NewsListViewModel) : NewsListNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavToDetailsFragmentObserver(fragment) {
            fragment.findNavController().navigate(
                NewsBookmarksFragmentDirections.actionNewsBookmarksFragmentToNewsDetailsFragment(
                    it
                )
            )
        }
    }
}