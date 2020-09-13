package com.rishabh.newsapp.news.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb.NewsSources.tableName
import com.rishabh.newsapp.news.storage.entity.NewsSourceDb.NewsSources.Column

@Entity(tableName = tableName)
data class NewsSourceDb(

    @PrimaryKey(autoGenerate = false)
    val id: String = "",

    @ColumnInfo(name = Column.name)
    val name: String? = null,

    @ColumnInfo(name = Column.description)
    val description: String? = null,

    @ColumnInfo(name = Column.url)
    val url: String? = null,

    @ColumnInfo(name = Column.category)
    val category: String? = null,

    @ColumnInfo(name = Column.language)
    val language: String? = null,

    @ColumnInfo(name = Column.country)
    val country: String? = null,

    ) {
    object NewsSources {
        const val tableName = "news_source"

        object Column {
            const val id = "id"
            const val name = "name"
            const val description = "description"
            const val url = "url"
            const val category = "category"
            const val language = "language"
            const val country = "country"
        }
    }
}