package com.shegor.samplenewsapp.base.news

import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

interface NewsRepoInitiating {

    fun getRepo() = NewsRepo(NewsApi.newsRetrofitService, newsDb)

}