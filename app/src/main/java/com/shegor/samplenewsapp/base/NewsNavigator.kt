package com.shegor.samplenewsapp.base

import androidx.fragment.app.Fragment

abstract class NewsNavigator() {

    abstract fun setNavigationObserver(fragment: Fragment)
}