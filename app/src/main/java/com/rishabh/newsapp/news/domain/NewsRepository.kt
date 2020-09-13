package com.rishabh.newsapp.news.domain

import com.rishabh.newsapp.core.ui.ViewState
import com.rishabh.newsapp.core.utils.httpError
import com.rishabh.newsapp.news.api.*
import com.rishabh.newsapp.news.storage.NewsArticlesDao
import com.rishabh.newsapp.news.storage.entity.NewsArticleDb
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface NewsRepository {

    fun getNewsArticles(newsId: String): Flow<ViewState<List<NewsArticleDb>>>
    fun getNewsSources(): Flow<ViewState<List<NewsSourceDb>>>

    suspend fun getNewsFromWebservice(newsId: String): Response<NewsResponse>
    suspend fun getSourcesFromWebservice(): Response<SourceResponse>
}

@Singleton
class DefaultNewsRepository @Inject constructor(
    private val newsDao: NewsArticlesDao,
    private val newsService: NewsService
) : NewsRepository {

    override fun getNewsArticles(newsId: String): Flow<ViewState<List<NewsArticleDb>>> = flow {
        emit(ViewState.loading())

        val freshNews = getNewsFromWebservice(newsId)
        freshNews.body()?.articles?.toStorage()?.let(newsDao::clearAndCacheArticles)

        val cachedNews = newsDao.getNewsArticles()
        emitAll(cachedNews.map { ViewState.success(it) })
    }.flowOn(Dispatchers.IO)

    override fun getNewsSources(): Flow<ViewState<List<NewsSourceDb>>> = flow {
        emit(ViewState.loading())
        val fetchSources = getSourcesFromWebservice()
        fetchSources.body()?.sources?.toStorage()?.let(newsDao::clearAndCacheSources)

        val cachedSources = newsDao.getNewsSources()
        emitAll(cachedSources.map { ViewState.success(it) })
    }.flowOn(Dispatchers.IO)

    override suspend fun getNewsFromWebservice(newsId: String): Response<NewsResponse> {
        return try {
            newsService.getTopHeadlines(newsId)
        } catch (e: Exception) {
            httpError(404)
        }
    }

    override suspend fun getSourcesFromWebservice(): Response<SourceResponse> {
        return try {
            newsService.getTopSources()
        } catch (e: Exception) {
            httpError(404)
        }
    }
}

private fun List<NewsArticle>.toStorage(): List<NewsArticleDb> {
    return this.map {
        NewsArticleDb(
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content,
            source = NewsArticleDb.Source(it.source.id, it.source.name)
        )
    }
}

@JvmName("toStorageNewsSource")
private fun List<NewsSource>.toStorage(): List<NewsSourceDb> {
    return this.map {
        NewsSourceDb(
            id = it.id,
            name = it.name,
            description = it.description,
            url = it.url,
            category = it.category,
            language = it.language,
            country = it.country
        )
    }
}

@Module
@InstallIn(ApplicationComponent::class)
interface NewsRepositoryModule {
    @Binds
    fun it(it: DefaultNewsRepository): NewsRepository
}