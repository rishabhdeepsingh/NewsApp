package com.rishabh.newsapp.news.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rishabh.newsapp.R
import com.rishabh.newsapp.core.utils.inflate
import com.rishabh.newsapp.news.api.NewsArticle
import com.rishabh.newsapp.news.storage.entity.NewsArticleDb
import com.rishabh.newsapp.news.ui.model.NewsDetailsFragment
import kotlinx.android.synthetic.main.row_news_article.view.*


class NewsArticlesAdapter :
    ListAdapter<NewsArticleDb, NewsArticlesAdapter.NewsHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsHolder(parent.inflate(R.layout.row_news_article))

    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) =
        newsHolder.bind(getItem(position))

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(newsArticle: NewsArticleDb) = with(itemView) {
            newsTitle.text = newsArticle.title
            newsAuthor.text = newsArticle.author
            newsPublishedAt.text = formatTime(newsArticle.publishedAt)
            Glide.with(this)
                .load(newsArticle.urlToImage)
                .placeholder(R.drawable.tools_placeholder)
                .into(newsImage)
            setOnClickListener {
                val activity = it.context as AppCompatActivity
                val detailsFragment = NewsDetailsFragment.newInstance(newsArticle)

                // Maybe change add to replace
                activity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_layout, detailsFragment, "newsDetails")
                    .addToBackStack(null)
                    .commit()
            }
        }

        private fun formatTime(time: String?): String? {
            return time?.substring(0, 19)?.split("T")?.joinToString(" ")
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsArticleDb>() {
            override fun areItemsTheSame(oldItem: NewsArticleDb, newItem: NewsArticleDb): Boolean =
                oldItem.publishedAt == newItem.publishedAt

            override fun areContentsTheSame(
                oldItem: NewsArticleDb,
                newItem: NewsArticleDb
            ): Boolean = oldItem == newItem
        }
    }
}

private fun NewsDetailsFragment.Companion.newInstance(newsArticleDb: NewsArticleDb): NewsDetailsFragment {
    val newsArticle = NewsArticle(
        description = newsArticleDb.description,
        source = NewsArticle.Source(id = newsArticleDb.source.id, name = newsArticleDb.source.name),
        title = newsArticleDb.title,
        urlToImage = newsArticleDb.urlToImage,
        url = newsArticleDb.url
    )
    return newInstance(newsArticle)
}
