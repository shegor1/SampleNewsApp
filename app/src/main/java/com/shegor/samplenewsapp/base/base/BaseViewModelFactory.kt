package com.shegor.samplenewsapp.base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val createViewModel: () -> T
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return createViewModel() as T
        }
        throw IllegalArgumentException("ViewModelClass Not Found")
    }
}