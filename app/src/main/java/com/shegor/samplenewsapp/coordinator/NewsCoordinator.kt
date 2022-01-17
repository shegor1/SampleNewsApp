package com.shegor.samplenewsapp.coordinator

import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsCoordinator(private val navigator: BaseNewsNavigator) {

    fun navigateToDetailsFragment(newsItem: NewsModel) = navigator.navigateToDetailsFragment(newsItem)
}