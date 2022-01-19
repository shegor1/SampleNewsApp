package com.shegor.samplenewsapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.shegor.samplenewsapp.persistentStorage.NewsDatabase
import com.shegor.samplenewsapp.persistentStorage.Prefs

lateinit var prefs: Prefs

lateinit var newsDb: NewsDatabase

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class App : Application() {

    companion object {

        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        newsDb = NewsDatabase.getInstance(this)
        instance = this
        prefs = Prefs(dataStore)

    }

}