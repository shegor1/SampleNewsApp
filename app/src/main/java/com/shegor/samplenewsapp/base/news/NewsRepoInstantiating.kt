package com.shegor.samplenewsapp.base.news

import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

interface NewsRepoInstantiating {

    fun getRepo() = NewsRepo(NewsApi.newsRetrofitService, newsDb)

}