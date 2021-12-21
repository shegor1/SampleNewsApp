package com.shegor.samplenewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNavMenu()
    }

    private fun setupBottomNavMenu() {

        val navController = findNavController(R.id.navHostFragment)
        binding.bottomNavMenu.setupWithNavController(navController)
    }
}