package com.shegor.samplenewsapp.base

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.R

interface BottomMenuReselection {

    fun setItemReselectedListener(
        activity: FragmentActivity,
        lifecycleScope: LifecycleCoroutineScope,
        navController: NavController,
        recyclerView: RecyclerView?
    ) {
        activity.findViewById<BottomNavigationView>(R.id.bottomNavMenu)
            ?.setOnItemReselectedListener {
                lifecycleScope.launchWhenResumed {

                    val currentDestination = navController.currentDestination

                    if (currentDestination?.parent?.startDestinationId ?: -1 == currentDestination?.id) {
                        recyclerView?.let {
                            it.layoutManager?.scrollToPosition(0)
                        }
                    } else {
                        activity.onBackPressed()
                    }
                }
            }
    }
}