package com.shegor.samplenewsapp.persistentStorage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shegor.samplenewsapp.R
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(val filterCountryStringId: Int)

class Prefs(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val FILTER_COUNTRY = intPreferencesKey("filter_country")
    }

    val userPreferencesFlow: LiveData<UserPreferences> = dataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> emit(emptyPreferences())
                else -> throw exception
            }
        }.map { preferences ->

            val filterCountry = preferences[PreferencesKeys.FILTER_COUNTRY] ?: R.string.russia
            UserPreferences(filterCountry)
        }
        .asLiveData()

    suspend fun updateFilterCountry(filterCountry: Int) {
        dataStore.edit {
            it[PreferencesKeys.FILTER_COUNTRY] = filterCountry
        }
    }
}