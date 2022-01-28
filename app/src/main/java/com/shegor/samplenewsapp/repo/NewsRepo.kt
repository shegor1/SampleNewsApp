package com.shegor.samplenewsapp.repo

import com.shegor.samplenewsapp.base.base.BaseRepository
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.models.NewsResponse
import com.shegor.samplenewsapp.persistentStorage.NewsDatabaseDao
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.service.NewsApiService
import com.shegor.samplenewsapp.service.NewsFilterCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsRepo(private val newsApiService: NewsApiService, private val newsDatabase: NewsDatabaseDao) :
    BaseRepository() {

    companion object {
        private const val networkErrorMessage = "Error fetching news"
    }

    suspend fun getLatestNews(country: String, category: NewsFilterCategory): List<NewsModel>? =
        getNewsData { newsApiService.getNewsDataAsync(country, category.categoryFilter).await() }


    suspend fun getNewsDataByQuery(query: String): List<NewsModel>? =
        getNewsData { newsApiService.getNewsDataByQueryAsync(query).await() }


    private suspend fun getNewsData(networkCall: suspend () -> Response<NewsResponse>): List<NewsModel>? =
        apiCall(call = networkCall, error = networkErrorMessage)?.articles?.toList()

    fun getLiveDataNewsFromDb() = newsDatabase.getAllNewsLiveData()

    fun getNewsFromDb() = newsDatabase.getAllNews()

    fun saveNewsItem(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            news.insertDate = System.currentTimeMillis()
            newsDatabase.insertNewsItem(news)
        }
    }

    fun deleteNewsItem(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDatabase.deleteNewsItem(news)
        }
    }

    fun getPrefsLiveData() = prefs.userPreferencesLiveData

    suspend fun updatePrefsFilterCountry(filterCountryStringId: Int) =
        prefs.updateFilterCountry(filterCountryStringId)
}