package com.shegor.samplenewsapp.base.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.R

interface BottomMenuReselection {

    val fragment: Fragment

    fun setItemReselectedListener(
        recyclerView: RecyclerView?
    ) {
        fragment.requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavMenu)
            ?.setOnItemReselectedListener {
                fragment.lifecycleScope.launchWhenResumed {

                    val currentDestination = fragment.findNavController().currentDestination

                    if (currentDestination?.parent?.startDestinationId ?: -1 == currentDestination?.id) {
                        recyclerView?.let {
                            it.layoutManager?.scrollToPosition(0)
                        }
                    } else {
                        fragment.requireActivity().onBackPressed()
                    }
                }
            }
    }
}