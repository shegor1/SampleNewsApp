package com.shegor.samplenewsapp.base.news

import com.shegor.samplenewsapp.models.NewsModel

abstract class BaseNewsNavigator {

    abstract fun navigateToDetailsFragment(newsItem: NewsModel)

}