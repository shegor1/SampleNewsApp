package com.shegor.samplenewsapp.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    var totalResults: Long,
    var articles: List<NewsModel>
)

@Parcelize
@Entity(tableName = "news_table")
data class NewsModel(

    @PrimaryKey()
    var title: String,

    @ColumnInfo(name = "publication_date") @Json(name = "publishedAt")
    var pubDate: String?,

    @ColumnInfo(name = "title_image_url") @Json(name = "urlToImage")
    var imageUrl: String?,

    var author: String?,

    @Json(name = "url")
    var sourceUrl: String?,

    //Описание новости взято за ее содержание, так как бесплатный новостной api
    // выдает некорректное содержание, в особенности для русских новостей
    @Json(name = "description")
    var description: String?,
) : Parcelable {

    var saved: Boolean = false

    var insertDate: Long = 0L
}

