package com.shegor.samplenewsapp.base

import androidx.fragment.app.Fragment

interface NewsNavigation {

    fun getNavigator(): NewsNavigator

    fun setNavigationObserver(fragment: Fragment) = getNavigator().setNavigationObserver(fragment)
}