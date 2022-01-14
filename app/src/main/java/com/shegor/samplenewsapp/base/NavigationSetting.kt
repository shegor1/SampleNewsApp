package com.shegor.samplenewsapp.base

import androidx.fragment.app.Fragment

interface NavigationSetting {

    fun getNavigator(): BaseNavigator

    fun setNavigationObserver(fragment: Fragment) = getNavigator().setupNavigation(fragment)
}