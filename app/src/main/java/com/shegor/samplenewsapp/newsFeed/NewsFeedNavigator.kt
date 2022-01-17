package com.shegor.samplenewsapp.newsFeed

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsFeedNavigator(val fragment: Fragment) : BaseNewsNavigator() {

    override fun navigateToDetailsFragment(newsItem: NewsModel) {
        fragment.findNavController().navigate(
            NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(newsItem)
        )
    }
}