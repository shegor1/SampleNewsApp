package com.shegor.samplenewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shegor.samplenewsapp.databinding.NewsItemBinding
import com.shegor.samplenewsapp.models.NewsModel


class NewsListAdapter(private val clickListener: NewsClickListener) :
    ListAdapter<NewsModel, NewsListAdapter.NewsItemViewHolder>(NewsDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class NewsItemViewHolder private constructor(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsModel, clickListener: NewsClickListener) {
            binding.news = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
                return NewsItemViewHolder(binding)
            }
        }
    }
}

class NewsDiffCallback : DiffUtil.ItemCallback<NewsModel>() {
    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem == newItem && oldItem.saved == newItem.saved
    }
}

class NewsClickListener(
    val newsClickListener: (newsItem: NewsModel, clickedViewId: Int) -> Unit
) {
    fun onClick(news: NewsModel, clickedViewId: Int) = newsClickListener(news, clickedViewId)
}