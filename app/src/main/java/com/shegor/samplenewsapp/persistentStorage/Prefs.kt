package com.shegor.samplenewsapp.persistentStorage

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shegor.samplenewsapp.R
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(val filterCountryStringId: Int)

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class Prefs(val context: Context) {

    private object PreferencesKeys {
        val FILTER_COUNTRY = intPreferencesKey("filter_country")
    }

    val userPreferencesFlow: LiveData<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->

            val filterCountry = preferences[PreferencesKeys.FILTER_COUNTRY] ?: R.string.russia
            UserPreferences(filterCountry)
        }
        .asLiveData()

    suspend fun updateFilterCountry(filterCountry: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FILTER_COUNTRY] = filterCountry
        }
    }
}