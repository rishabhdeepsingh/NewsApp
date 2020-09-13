package com.rishabh.newsapp.news.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rishabh.newsapp.R
import com.rishabh.newsapp.core.ui.ViewState
import com.rishabh.newsapp.core.ui.base.BaseActivity
import com.rishabh.newsapp.core.utils.observeNotNull
import com.rishabh.newsapp.core.utils.toast
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb
import com.rishabh.newsapp.news.ui.adapter.NewsSourceAdapter
import com.rishabh.newsapp.news.ui.viewmodel.NewsSourceViewModel
import kotlinx.android.synthetic.main.activity_sources.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*


class NewsSourceActivity : BaseActivity() {
    private val newsSourceViewModel: NewsSourceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        newsSourceList.setEmptyView(empty_view)
        newsSourceList.setProgressView(progress_view)
        val adapter = NewsSourceAdapter()

        newsSourceList.adapter = adapter
        newsSourceList.layoutManager = LinearLayoutManager(this)

        newsSourceViewModel.getNewsSources().observeNotNull(this, {
            when (it) {
                is ViewState.Success -> {
                    newsSourceViewModel.getNewsSources().removeObservers(this)
                    adapter.submitList(it.data)
                }
                is ViewState.Loading -> newsSourceList.showLoading()
                is ViewState.Error -> toast("Something went wrong => ${it.message}")
            }
        })
    }

}