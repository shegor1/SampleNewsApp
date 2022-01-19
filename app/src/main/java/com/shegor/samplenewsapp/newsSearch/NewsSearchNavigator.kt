package com.shegor.samplenewsapp.newsSearch

import androidx.fragment.app.FragmentActivity
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsSearchNavigator(fragmentActivity: FragmentActivity) :
    BaseNewsNavigator(fragmentActivity) {

    override fun navigateToDetailsFragment(newsItem: NewsModel) =
        navigate(NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailsFragment(newsItem))
}