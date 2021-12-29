package com.shegor.samplenewsapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo

abstract class InternetNewsListViewModel(newsRepo: NewsRepo) : NewsViewModel(newsRepo) {

    protected val _news = MutableLiveData<List<NewsModel>>()
    val news: LiveData<List<NewsModel>>
        get() = _news

    val savedNewsLiveData = newsRepo.getNewsFromDb()

    var savedNews: List<NewsModel>? = null

    fun checkForDbChanges(dbNewsList: List<NewsModel>): List<NewsModel>? {
        val networkNewsList = news.value
        networkNewsList?.forEach { networkNewsItem ->
            networkNewsItem.saved = dbNewsList.contains(networkNewsItem)
        }
        return networkNewsList
    }
}