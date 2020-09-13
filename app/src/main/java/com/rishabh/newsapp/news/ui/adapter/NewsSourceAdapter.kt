package com.rishabh.newsapp.news.ui.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rishabh.newsapp.R
import com.rishabh.newsapp.core.utils.inflate
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb
import com.rishabh.newsapp.news.ui.activity.NewsActivity
import kotlinx.android.synthetic.main.row_news_source.view.*


class NewsSourceAdapter :
    ListAdapter<NewsSourceDb, NewsSourceAdapter.NewsSourceHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsSourceHolder(parent.inflate(R.layout.row_news_source))

    override fun onBindViewHolder(newsSourceHolder: NewsSourceHolder, position: Int) =
        newsSourceHolder.bind(getItem(position))

    class NewsSourceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsSource: NewsSourceDb) = with(itemView) {
            newsSourceName.text = newsSource.name
            newsSourceCategory.text = newsSource.category
            newsSourceCountry.text = newsSource.country
            newsSourceURL.text = newsSource.url

            setOnClickListener {
                Toast.makeText(it.context, newsSource.name, Toast.LENGTH_SHORT).show()
                val myIntent = Intent(it.context, NewsActivity::class.java)
                myIntent.putExtra("id", newsSource.id)
                myIntent.putExtra("newsSource", newsSource.name)
                it.context.startActivity(myIntent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsSourceDb>() {
            override fun areItemsTheSame(oldItem: NewsSourceDb, newItem: NewsSourceDb): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NewsSourceDb,
                newItem: NewsSourceDb
            ): Boolean = oldItem == newItem
        }
    }

}