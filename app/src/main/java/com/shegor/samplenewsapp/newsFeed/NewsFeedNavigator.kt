package com.shegor.samplenewsapp.newsFeed

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.NewsNavigator

class NewsFeedNavigator(viewModel: NewsFeedViewModel) : NewsNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavigationObserver(fragment){
            fragment.findNavController().navigate(
                NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(
                    it
                )
            )
        }

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