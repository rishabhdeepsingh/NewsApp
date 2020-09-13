package com.rishabh.newsapp.news.api

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("sources")
    val sources: List<NewsSource> = emptyList()
)