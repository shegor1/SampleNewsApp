package com.shegor.samplenewsapp.base.networkNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.shegor.samplenewsapp.DeletedNews
import com.shegor.samplenewsapp.base.newsList.BaseNewsListViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.DateUtils
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseNetworkNewsListViewModel(
    override val newsRepo: NewsRepo,
    navigateToDetails: ((newsItem: NewsModel) -> Unit)?
) : BaseNewsListViewModel(newsRepo, navigateToDetails) {

    private val _news = MutableLiveData<List<NewsModel>>()
    override val news: LiveData<List<NewsModel>>
        get() = _news

    private var deletedNewsObserver: Observer<String> = Observer{ deletedNewsTitle ->
        deletedNewsTitle?.let { title ->
            _news.value?.let { newsList ->
                newsList.map { if (it.title == title) it.saved = false }
            }
        }
    }

    init {
        DeletedNews.newsToDelete.observeForever(deletedNewsObserver)
    }

    protected fun getNewsData(networkRepoCall: suspend () -> List<NewsModel>?) {
        _status.value = NewsLoadingStatus.LOADING
        viewModelScope.launch {

            var newsData = networkRepoCall.invoke()
                ?: return@launch processingNullApiResponse()

            if (newsData.isEmpty()) {
                _status.value = NewsLoadingStatus.NO_RESULTS
                _news.value = newsData
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

    private fun checkIfSaved(
        networkNewsList: List<NewsModel>,
        savedNewsList: List<NewsModel>
    ): List<NewsModel> {

        return networkNewsList.map { networkNewsItem ->
            networkNewsItem.saved = savedNewsList.contains(networkNewsItem)
            networkNewsItem
        }
    }

    private fun sortNewsByDate(newsList: List<NewsModel>): List<NewsModel> =
        newsList.sortedByDescending { DateUtils.jsonDateToLocalDate(it.pubDate) }


    override fun onCleared() {
        DeletedNews.newsToDelete.removeObserver(deletedNewsObserver)

        super.onCleared()
    }
}