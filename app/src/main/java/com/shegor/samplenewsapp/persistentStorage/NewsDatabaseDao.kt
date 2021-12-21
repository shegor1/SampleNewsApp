package com.shegor.samplenewsapp.persistentStorage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shegor.samplenewsapp.models.NewsModel

@Dao
interface NewsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsItem(news: NewsModel)

    @Query("SELECT * from news_table order by insertDate desc")
    fun getAllNews(): LiveData<List<NewsModel>>

    @Query("SELECT * from news_table")
    fun getAllNewsStatic(): List<NewsModel>

    @Query("SELECT * from news_table WHERE title = :newsTitle")
    fun getNewsByTitle(newsTitle: String): NewsModel

    @Delete
    fun deleteNewsItem(news: NewsModel)
}