package com.shegor.samplenewsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.FragmentNewsSettingsBinding
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.viewModels.NewsSettingsViewModel


class NewsSettingsFragment : Fragment() {

    companion object {
        const val FILTER_COUNTRY_MENU_GROUP_ID = 1
    }

    private lateinit var binding: FragmentNewsSettingsBinding

    private val settingsViewModel: NewsSettingsViewModel by lazy {
        ViewModelProvider(this).get(NewsSettingsViewModel::class.java)
    }

    private lateinit var popupMenu: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_settings, container, false)


        setupCountrySelectPopupMenu()
        setupObservers()

        return binding.root
    }


    private fun setupObservers() {
        prefs.userPreferencesFlow.observe(viewLifecycleOwner) {
            binding.currentFilterCountry.text = requireContext().getString(it.filterCountryStringId)
        }
    }

    private fun setupCountrySelectPopupMenu() {
        binding.availableCountriesChevronDown.setOnClickListener {

            popupMenu = PopupMenu(this.context, binding.availableCountriesChevronDown)
            popupMenu.menuInflater.inflate(R.menu.countries_popup_menu, popupMenu.menu)

            val countries =
                FILTER_COUNTRIES.map { Pair(it.key, requireContext().getString(it.key)) }
            var count = 1

            for (country in countries.sortedBy { it.second }) {
                popupMenu.menu.add(
                    FILTER_COUNTRY_MENU_GROUP_ID,
                    country.first,
                    count,
                    country.second
                )
                count += 1
            }
            setupCountriesMenuListeners()
            popupMenu.show()
        }
    }

    private fun setupCountriesMenuListeners() {

        popupMenu.setOnMenuItemClickListener { menuItem ->

            saveChosenCountry(menuItem.itemId)
            binding.currentFilterCountry.text = menuItem.title

            true
        }
    }

    private fun saveChosenCountry(countryStringId: Int) {
        settingsViewModel.saveChosenCountry(countryStringId)
    }

}