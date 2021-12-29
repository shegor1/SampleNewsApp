package com.shegor.samplenewsapp.base

import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

interface NewsRepoInstantiating {

    fun getRepo() = NewsRepo(NewsApi.newsRetrofitService, newsDb)

}