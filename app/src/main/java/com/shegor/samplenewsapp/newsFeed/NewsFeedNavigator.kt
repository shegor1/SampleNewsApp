package com.shegor.samplenewsapp.newsFeed

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.NewsNavigator

class NewsFeedNavigator(private val viewModel: NewsFeedViewModel) : NewsNavigator() {

    override fun setNavigationObserver(fragment: Fragment) {

        viewModel.navigationToDetailsFragment.observe(fragment.viewLifecycleOwner, { newsItem ->
            newsItem?.let {
                fragment.findNavController().navigate(
                    NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(
                        it
                    )
                )
                viewModel.finishNavigationToDetailsFragment()
            }
        })
    }
}