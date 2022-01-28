package com.shegor.samplenewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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
        binding.bottomNavMenu.setupWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}