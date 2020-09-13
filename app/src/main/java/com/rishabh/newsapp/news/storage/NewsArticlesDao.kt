package com.rishabh.newsapp.news.storage

import androidx.room.*
import com.rishabh.newsapp.news.storage.entity.NewsArticleDb
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<NewsArticleDb>): List<Long>

    @Query("DELETE FROM news_article")
    fun clearAllArticles()

    @Transaction
    fun clearAndCacheArticles(articles: List<NewsArticleDb>) {
        clearAllArticles()
        insertArticles(articles)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(articles: List<NewsSourceDb>): List<Long>

    @Query("DELETE FROM news_source")
    fun clearAllSources()

    @Transaction
    fun clearAndCacheSources(sources: List<NewsSourceDb>) {
        clearAllSources()
        insertSources(sources)
    }


    @Query("SELECT * FROM news_article")
    fun getNewsArticles(): Flow<List<NewsArticleDb>>

    @Query("SELECT * FROM news_source")
    fun getNewsSources(): Flow<List<NewsSourceDb>>
}