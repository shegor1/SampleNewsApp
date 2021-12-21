package com.shegor.samplenewsapp.persistentStorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shegor.samplenewsapp.models.NewsModel

@Database(entities = [NewsModel::class], version = 3, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDatabaseDao

    companion object {

        @Volatile
        private var INSTANSE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {

            synchronized(this) {

                var instance = INSTANSE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANSE = instance
                }
                return instance
            }
        }
    }
}