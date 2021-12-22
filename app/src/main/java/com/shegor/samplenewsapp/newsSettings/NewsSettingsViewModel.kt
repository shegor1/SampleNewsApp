package com.shegor.samplenewsapp.newsSettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegor.samplenewsapp.prefs
import kotlinx.coroutines.launch

class NewsSettingsViewModel : ViewModel() {

    fun saveChosenCountry(country: Int) {
        viewModelScope.launch {
            prefs.updateFilterCountry(country)
        }
    }
}