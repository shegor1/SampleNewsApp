package com.shegor.samplenewsapp.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
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
fun setNewsTitleFormatted(textView: TextView, newsTitle: String?) {
    newsTitle?.let {
        textView.text = newsTitle
    }
}

@BindingAdapter("newsPubDateFormatted")
fun setNewsPubDateFormatted(textView: TextView, newsPubDate: String?) {
    textView.text = DateUtils.jsonDateToFormattedDate(newsPubDate, textView.context)
}

@BindingAdapter("newsLink")
fun setNewsLink(textView: TextView, newsLink: String?) {

    textView.apply {
        text = resources.getString(R.string.full_news_link_title, newsLink)

        val linkTextColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            resources.getColor(R.color.blue, context.theme) else resources.getColor(R.color.blue)
        setLinkTextColor(linkTextColor)
    }
}

@BindingAdapter("newsImage")
fun setNewsImage(imageView: ImageView, newsImgUrl: String?) {

    newsImgUrl?.let {

        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .error(R.drawable.image_placeholder)
            .placeholder(ColorDrawable(Color.GRAY))
            .into(imageView)
        return@setNewsImage
    }
    imageView.setImageResource(R.drawable.image_placeholder)

}

@BindingAdapter(value = ["clickListener", "newsItem"])
fun onBookmarkIconClickListener(view: View, clickListener: NewsClickListener, newsItem: NewsModel) {
    view.setOnClickListener { view ->
        if (view is Button) {
            val buttonBackgroundResId =
                if (newsItem.saved) R.drawable.ic_bookmark else R.drawable.ic_bookmark_saved
            view.background =
                ResourcesCompat.getDrawable(view.resources, buttonBackgroundResId, null)
        }
        clickListener.onClick(newsItem, view.id)
    }
}

@BindingAdapter("progressBarStatus")
fun manageProgressBar(progressBar: ProgressBar, status: NewsLoadingStatus?) {
    status?.let { status ->
        progressBar.visibility =
            if (status == NewsLoadingStatus.LOADING) View.VISIBLE else View.GONE
    }
}


@BindingAdapter("placeHolderImgStatus")
fun manageStatusImage(imageView: ImageView, status: NewsLoadingStatus?) {
    status?.let { status ->
        when (status) {
            NewsLoadingStatus.SEARCH_INIT -> {
                imageView.setImageResource(R.drawable.init_search)
                imageView.visibility = View.VISIBLE
            }

            NewsLoadingStatus.LOADING -> {
                imageView.visibility = View.GONE
            }

            NewsLoadingStatus.NO_RESULTS -> {
                imageView.setImageResource(R.drawable.no_results_found)
                imageView.visibility = View.VISIBLE
            }

            NewsLoadingStatus.INIT_ERROR -> {
                imageView.setImageResource(R.drawable.ic_internet_error)
                imageView.visibility = View.VISIBLE
            }
            NewsLoadingStatus.DONE -> {
                imageView.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("refreshAnimStatus")
fun manageRefresh(swipeRefreshLayout: SwipeRefreshLayout, status: NewsLoadingStatus?) {
    status?.let { status ->
        swipeRefreshLayout.isRefreshing = status == NewsLoadingStatus.LOADING
    }
}
