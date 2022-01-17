package com.shegor.samplenewsapp.base.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shegor.samplenewsapp.base.news.BaseNewsViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

abstract class BaseNewsListViewModel(override val newsRepo: NewsRepo, private var navigateToDetails : ((newsItem: NewsModel) -> Unit)?) : BaseNewsViewModel(newsRepo){

    abstract val news: LiveData<List<NewsModel>>

    protected val _status = MutableLiveData<NewsLoadingStatus>()
    val status: LiveData<NewsLoadingStatus>
        get() = _status


    fun navigateToDetailsFragment(newsItem: NewsModel) {
        navigateToDetails?.invoke(newsItem)
    }

    override fun onCleared() {
        navigateToDetails = null
        super.onCleared()
    }
}