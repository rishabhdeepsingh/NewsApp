package com.rishabh.newsapp.news.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
        supportActionBar?.title = if (isOnline(this)) newsSource else "Offline"

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

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
