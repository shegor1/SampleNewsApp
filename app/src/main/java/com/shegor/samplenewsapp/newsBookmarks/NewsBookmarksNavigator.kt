package com.shegor.samplenewsapp.newsBookmarks

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.NewsNavigator
import com.shegor.samplenewsapp.base.NewsListViewModel

class NewsBookmarksNavigator(viewModel: NewsListViewModel) : NewsNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavigationObserver(fragment) {
            fragment.findNavController().navigate(
                NewsBookmarksFragmentDirections.actionNewsBookmarksFragmentToNewsDetailsFragment(
                    it
                )
            )
        }
    }
}