package com.rishabh.newsapp.news.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rishabh.newsapp.core.ui.ViewState
import com.rishabh.newsapp.news.domain.NewsRepository
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb

class NewsSourceViewModel @ViewModelInject constructor(
    newsRepository: NewsRepository
) : ViewModel() {

    private val newsSourceDb: LiveData<ViewState<List<NewsSourceDb>>> =
        newsRepository.getNewsSources().asLiveData()

    fun getNewsSources(): LiveData<ViewState<List<NewsSourceDb>>> = newsSourceDb
}