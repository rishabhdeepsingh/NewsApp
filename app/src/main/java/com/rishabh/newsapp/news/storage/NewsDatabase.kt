package com.rishabh.newsapp.news.storage

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rishabh.newsapp.news.storage.entity.NewsArticleDb
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb

@Database(
    entities = [NewsArticleDb::class, NewsSourceDb::class],
    version = NewsDatabaseMigration.latestVersion
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsArticlesDao(): NewsArticlesDao

    companion object {

        private const val databaseName = "news-db"

        fun buildDefault(context: Context) =
            Room.databaseBuilder(context, NewsDatabase::class.java, databaseName)
                .addMigrations(*NewsDatabaseMigration.allMigrations)
                .build()

    }
}