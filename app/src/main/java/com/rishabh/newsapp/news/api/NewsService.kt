package com.rishabh.newsapp.news.api

import com.rishabh.newsapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


const val TECHNOLOGY = "technology"

interface NewsService {

    @GET("sources?apiKey=${BuildConfig.NEWS_API_KEY}&language=en")
    suspend fun getTopSources(): Response<SourceResponse>

    @GET("top-headlines?apiKey=${BuildConfig.NEWS_API_KEY}&language=en")
    suspend fun getTopHeadlines(
        @Query("sources") category: String = TECHNOLOGY
    ): Response<NewsResponse>
}
