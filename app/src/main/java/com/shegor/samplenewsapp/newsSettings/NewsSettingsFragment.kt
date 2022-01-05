package com.shegor.samplenewsapp.newsSettings

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.BaseFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsSettingsBinding
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES

class NewsSettingsFragment : BaseFragment<NewsSettingsViewModel, FragmentNewsSettingsBinding>() {

    companion object {
        const val FILTER_COUNTRY_MENU_GROUP_ID = 1
    }

    private lateinit var popupMenu: PopupMenu

    override fun getViewModel() = NewsSettingsViewModel::class.java

    override val layoutId = R.layout.fragment_news_settings

    override fun getViewModelFactory() = defaultViewModelProviderFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCountrySelectPopupMenu()
        setupObservers()
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

            countries.sortedBy { it.second }.forEachIndexed { index, country ->
                popupMenu.menu.add(
                    FILTER_COUNTRY_MENU_GROUP_ID,
                    country.first,
                    index,
                    country.second
                )
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
        viewModel.saveChosenCountry(countryStringId)
    }


}