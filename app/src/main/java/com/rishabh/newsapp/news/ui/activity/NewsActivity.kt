package com.rishabh.newsapp.news.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rishabh.newsapp.R
import com.rishabh.newsapp.core.ui.ViewState
import com.rishabh.newsapp.core.ui.base.BaseActivity
import com.rishabh.newsapp.core.utils.observeNotNull
import com.rishabh.newsapp.core.utils.toast
import com.rishabh.newsapp.news.ui.adapter.NewsArticlesAdapter
import com.rishabh.newsapp.news.ui.viewmodel.NewsArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*


class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsSourceId = intent.getStringExtra("id") ?: "abc-news"
        val newsSource = intent.getStringExtra("newsSource") ?: "News App"
        supportActionBar?.title = newsSource

        newsList.setEmptyView(empty_view)
        newsList.setProgressView(progress_view)

        val adapter = NewsArticlesAdapter()
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this)

        val newsArticleViewModel: NewsArticleViewModel by viewModels()
        newsArticleViewModel.getNewsArticles(newsSourceId).observeNotNull(this) {
            when (it) {
                is ViewState.Success -> adapter.submitList(it.data)
                is ViewState.Loading -> newsList.showLoading()
                is ViewState.Error -> toast("Something went wrong => ${it.message}")
            }
        }

    }
}
