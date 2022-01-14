package com.shegor.samplenewsapp.newsFeed

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.newsList.NewsListNavigator

class NewsFeedNavigator(viewModel: NewsFeedViewModel) : NewsListNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavToDetailsFragmentObserver(fragment) {
            fragment.findNavController().navigate(
                NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(
                    it
                )
            )
        }
    }
}