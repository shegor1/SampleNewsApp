package com.shegor.samplenewsapp.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shegor.samplenewsapp.models.NewsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"
private const val API_KEY = "43234d7b1580409080568ec28d1c2402"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface NewsApiService {

    @GET("top-headlines")
    fun getNewsDataAsync(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Response<NewsResponse>>

    @GET("everything")
    fun getNewsDataByQueryAsync(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Response<NewsResponse>>
}

object NewsApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val newsRetrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}