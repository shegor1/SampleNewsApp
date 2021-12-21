package com.shegor.samplenewsapp.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.models.NewsModel

enum class NewsLoadingStatus { LOADING, DONE, INIT_ERROR, REFRESHING_ERROR, NO_RESULTS, SEARCH_INIT }

@BindingAdapter("newsTitleFormatted")
fun TextView.setNewsTitleFormatted(newsTitle: String?) {
    newsTitle?.let {
        text = newsTitle
    }
}

@BindingAdapter("newsPubDateFormatted")
fun TextView.setNewsPubDateFormatted(newsPubDate: String?) {

    text = DateUtils.jsonDateToFormattedDate(newsPubDate, context)
}

@BindingAdapter("newsLink")
fun TextView.setNewsLink(newsLink: String?) {

    text = resources.getString(R.string.full_news_link_title, newsLink)
    setLinkTextColor(resources.getColor(R.color.blue, context.theme))
}

@BindingAdapter("newsImage")
fun ImageView.setNewsImage(newsImgUrl: String?) {

    if (newsImgUrl != null) {

        val imgUri = newsImgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .error(R.drawable.no_image_placeholder)
            .placeholder(ColorDrawable(Color.GRAY))
            .into(this)
    } else {
        this.setImageResource(R.drawable.no_image_placeholder)
    }
}

@BindingAdapter(value = ["clickListener", "newsItem"])
fun View.onBookmarkIconClickListener(clickListener: NewsClickListener, newsItem: NewsModel) {
    setOnClickListener { view ->
        if (view is Button) {
            view.background =
                if (newsItem.saved) {
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark, null)
                } else {
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark_saved, null)
                }
        }
        clickListener.onClick(newsItem, view.id)
    }
}

@BindingAdapter("progressBarStatus")
fun ProgressBar.manageProgressBar(status: NewsLoadingStatus?) {
    status?.let { status ->
        when (status) {
            NewsLoadingStatus.LOADING -> this.visibility = View.VISIBLE
            else -> this.visibility = View.GONE

        }
    }
}

@BindingAdapter("placeHolderImgStatus")
fun ImageView.manageStatusImage(status: NewsLoadingStatus?) {
    status?.let { status ->
        when (status) {
            NewsLoadingStatus.SEARCH_INIT -> {
                this.setImageResource(R.drawable.init_search)
                this.visibility = View.VISIBLE
            }

            NewsLoadingStatus.LOADING -> {
                this.visibility = View.GONE
            }

            NewsLoadingStatus.NO_RESULTS -> {
                this.setImageResource(R.drawable.no_results_found)
                this.visibility = View.VISIBLE
            }

            NewsLoadingStatus.INIT_ERROR -> {
                this.setImageResource(R.drawable.ic_internet_error)
                this.visibility = View.VISIBLE
            }
            NewsLoadingStatus.DONE -> {
                this.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("refreshAnimStatus")
fun SwipeRefreshLayout.manageRefresh(status: NewsLoadingStatus?) {
    status?.let { status ->
        when (status) {
            NewsLoadingStatus.LOADING -> this.isRefreshing = true
            else -> this.isRefreshing = false
        }
    }
}