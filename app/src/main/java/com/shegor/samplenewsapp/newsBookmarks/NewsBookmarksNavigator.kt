package com.shegor.samplenewsapp.newsBookmarks

import androidx.fragment.app.FragmentActivity
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsBookmarksNavigator(fragmentActivity: FragmentActivity) :
    BaseNewsNavigator(fragmentActivity) {

    override fun navigateToDetailsFragment(newsItem: NewsModel) =
        navigate(
            NewsBookmarksFragmentDirections.actionNewsBookmarksFragmentToNewsDetailsFragment(
                newsItem
            )
        )
}