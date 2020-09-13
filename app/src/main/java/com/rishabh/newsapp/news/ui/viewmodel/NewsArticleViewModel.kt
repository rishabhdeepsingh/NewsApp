package com.rishabh.newsapp.news.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rishabh.newsapp.core.ui.ViewState
import com.rishabh.newsapp.news.domain.NewsRepository
import com.rishabh.newsapp.news.storage.entity.NewsArticleDb

class NewsArticleViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    fun getNewsArticles(newsId: String): LiveData<ViewState<List<NewsArticleDb>>> {
        return newsRepository.getNewsArticles(newsId).asLiveData()
    }
}