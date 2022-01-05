package com.shegor.samplenewsapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.DateUtils
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class InternetNewsListViewModel(newsRepo: NewsRepo) : NewsListViewModel(newsRepo),
    InternetNewsListObserversSetting {

    private val _news = MutableLiveData<List<NewsModel>>()
    override val news: LiveData<List<NewsModel>>
        get() = _news

    val savedNewsLiveData = newsRepo.getLiveDataNewsFromDb()

    var savedNews: List<NewsModel>? = null

    protected fun getNewsData(networkRepoCall: suspend () -> List<NewsModel>?) {
        viewModelScope.launch {

            var newsData = networkRepoCall.invoke()
                ?: return@launch processingNullApiResponse()

            if (newsData.isEmpty()) {
                _status.value = NewsLoadingStatus.NO_RESULTS
                return@launch
            }

            withContext(Dispatchers.IO) {

                newsData = checkIfSaved(newsData, newsRepo.getNewsFromDb())
                newsData = sortNewsByDate(newsData)
                withContext(Dispatchers.Main) {
                    _news.value = newsData
                    _status.value = NewsLoadingStatus.DONE
                }
            }
        }
    }

    private fun processingNullApiResponse() {
        if (_news.value == null) _status.value = NewsLoadingStatus.INIT_ERROR
        else _status.value = NewsLoadingStatus.REFRESHING_ERROR
    }

    fun checkIfSaved(networkNewsList: List<NewsModel>, savedNewsList: List<NewsModel>): List<NewsModel> {

        return networkNewsList.map { networkNewsItem ->
            networkNewsItem.saved = savedNewsList.contains(networkNewsItem)
            networkNewsItem
        }
    }

    private fun sortNewsByDate(newsList: List<NewsModel>): List<NewsModel> =
        newsList.sortedByDescending { DateUtils.jsonDateToLocalDate(it.pubDate) }

}