package com.shegor.samplenewsapp.base

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
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
            ?.setOnItemReselectedListener { item ->
                lifecycleScope.launchWhenResumed {
                    val reselectedDestinationId = item.itemId
                    if (navController.currentDestination?.id ?: -1 == reselectedDestinationId) {
                        recyclerView?.let {
                            it.layoutManager?.scrollToPosition(0)
                        }
                    } else {
                        navController.popBackStack(reselectedDestinationId, inclusive = false)
                    }
                }
            }
    }
}