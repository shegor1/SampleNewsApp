package com.shegor.samplenewsapp.newsSettings

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegor.samplenewsapp.repo.NewsRepo
import kotlinx.coroutines.launch

class NewsSettingsViewModel(val newsRepo: NewsRepo) : ViewModel() {

    val filterCountryLiveData =
        Transformations.map(newsRepo.getPrefsLiveData()) { it.filterCountryStringId }

    fun saveChosenCountry(country: Int) {
        viewModelScope.launch {
            newsRepo.updatePrefsFilterCountry(country)
        }
    }
}