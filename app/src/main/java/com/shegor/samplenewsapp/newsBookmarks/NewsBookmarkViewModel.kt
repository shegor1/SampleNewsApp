package com.shegor.samplenewsapp.newsBookmarks

import android.app.Application
import androidx.lifecycle.*
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi


class NewsBookmarkViewModel(application: Application) : AndroidViewModel(application) {

    var newsRepo = NewsRepo(
        NewsApi.newsRetrofitService, newsDb
    )

    private val _status = MutableLiveData<NewsLoadingStatus>()
    val status: LiveData<NewsLoadingStatus>
        get() = _status

    private val _navigationToDetailsFragment = MutableLiveData<NewsModel?>()
    val navigationToDetailsFragment: LiveData<NewsModel?>
        get() = _navigationToDetailsFragment

    val news = getSavedNews()

    private fun getSavedNews(): LiveData<List<NewsModel>> {

        _status.value = NewsLoadingStatus.LOADING

        val savedNews = newsRepo.getLiveDataNewsFromDb()
        _status.value = NewsLoadingStatus.DONE
        return savedNews

    }

    fun navigateToDetailsFragment(newsItem: NewsModel) {
        _navigationToDetailsFragment.value = newsItem
    }

    fun finishNavigationToDetailsFragment() {
        _navigationToDetailsFragment.value = null
    }

    fun deleteBookmark(newsItem: NewsModel) {
        newsRepo.deleteNewsItem(newsItem)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsBookmarkViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsBookmarkViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}