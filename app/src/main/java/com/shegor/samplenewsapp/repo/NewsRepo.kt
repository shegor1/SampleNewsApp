package com.shegor.samplenewsapp.repo

import androidx.lifecycle.LiveData
import com.shegor.samplenewsapp.service.NewsApiService
import com.shegor.samplenewsapp.persistentStorage.NewsDatabase
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.models.Response
import com.shegor.samplenewsapp.service.NewsApiFilterCategory
import com.shegor.samplenewsapp.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsRepo(private val newsApiService: NewsApiService, private val newsDatabase: NewsDatabase) {

    fun getAllNewsData(
        country: String,
        category: NewsApiFilterCategory,
        viewModelScope: CoroutineScope,
        callback: (List<NewsModel>?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = newsApiService.getNewsData(country, category.categoryFilter)
                processingResponse(response, callback)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }


    fun getNewsDataByQuery(
        query: String,
        viewModelScope: CoroutineScope,
        callback: (List<NewsModel>?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = newsApiService.getNewsDataByQuery(query)
                processingResponse(response, callback)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    private suspend fun processingResponse(
        response: Response?,
        callback: (List<NewsModel>?) -> Unit
    ) {
        var newsList = response?.articles ?: return callback(listOf())

        withContext(Dispatchers.IO) {

            newsList = checkIfSaved(newsList)
            newsList = sortNewsByDate(newsList)

            withContext(Dispatchers.Main) {
                callback(newsList)
            }
        }
    }

    private fun checkIfSaved(networkNewsList: List<NewsModel>): List<NewsModel> {
        val savedNewsList = newsDatabase.newsDao.getAllNewsStatic()

        networkNewsList.forEach { networkNewsItem ->
            networkNewsItem.saved = savedNewsList.contains(networkNewsItem)
        }
        return networkNewsList
    }

    private fun sortNewsByDate(newsList: List<NewsModel>): List<NewsModel> {
        return newsList.sortedByDescending { DateUtils.jsonDateToLocalDate(it.pubDate) }
    }

    fun getNewsFromDb(): LiveData<List<NewsModel>> {
        return newsDatabase.newsDao.getAllNews()
    }

    fun saveNewsItem(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            news.insertDate = System.currentTimeMillis()
            newsDatabase.newsDao.insertNewsItem(news)
        }
    }

    fun deleteNewsItem(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDatabase.newsDao.deleteNewsItem(news)
        }
    }
}