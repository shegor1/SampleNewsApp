package com.shegor.samplenewsapp.service

import android.os.Parcelable
import com.shegor.samplenewsapp.R
import kotlinx.parcelize.Parcelize

val FILTER_COUNTRIES = mapOf(
    R.string.united_arab_emirates to "ae",
    R.string.argentina to "ar",
    R.string.austria to "at",
    R.string.australia to "au",
    R.string.belgium to "be",
    R.string.bulgaria to "bg",
    R.string.brazil to "br",
    R.string.canada to "ca",
    R.string.switzerland to "ch",
    R.string.china to "cn",
    R.string.colombia to "co",
    R.string.cuba to "cu",
    R.string.czech_republic to "cz",
    R.string.germany to "de",
    R.string.egypt to "eg",
    R.string.france to "fr",
    R.string.united_kingdom to "gb",
    R.string.greece to "gr",
    R.string.hong_kong to "hk",
    R.string.hungary to "hu",
    R.string.indonesia to "id",
    R.string.ireland to "ie",
    R.string.israel to "il",
    R.string.india to "in",
    R.string.italy to "it",
    R.string.japan to "jp",
    R.string.south_korea to "kr",
    R.string.lithuania to "lt",
    R.string.latvia to "lv",
    R.string.morocco to "ma",
    R.string.mexico to "mx",
    R.string.malaysia to "my",
    R.string.nigeria to "ng",
    R.string.netherlands to "nl",
    R.string.norway to "no",
    R.string.new_zealand to "nz",
    R.string.philippines to "ph",
    R.string.poland to "pl",
    R.string.portugal to "pt",
    R.string.romania to "ro",
    R.string.serbia to "rs",
    R.string.russia to "ru",
    R.string.saudi_arabia to "sa",
    R.string.sweden to "se",
    R.string.singapore to "sg",
    R.string.slovenia to "si",
    R.string.slovakia to "sk",
    R.string.thailand to "th",
    R.string.turkey to "tr",
    R.string.taiwan to "tw",
    R.string.ukraine to "ua",
    R.string.usa to "us",
    R.string.venezuela to "ve",
    R.string.south_africa to "za"
)

@Parcelize
enum class NewsFilterCategory(val categoryFilter: String, val categoryNameResId: Int) :
    Parcelable {
    GENERAL("general", R.string.filter_general),
    BUSINESS("business", R.string.filter_business),
    ENTERTAINMENT("entertainment", R.string.filter_entertainment),
    HEALTH("health", R.string.filter_health),
    SCIENCE("science", R.string.filter_science),
    SPORTS("sports", R.string.filter_sports),
    TECHNOLOGY("technology", R.string.filter_technology)
}