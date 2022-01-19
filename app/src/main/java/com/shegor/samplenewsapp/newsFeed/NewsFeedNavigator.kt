package com.shegor.samplenewsapp.newsFeed

import androidx.fragment.app.FragmentActivity
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsFeedNavigator(fragmentActivity: FragmentActivity) :
    BaseNewsNavigator(fragmentActivity) {

    override fun navigateToDetailsFragment(newsItem: NewsModel) =
        navigate(
            NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(newsItem)
        )
}
