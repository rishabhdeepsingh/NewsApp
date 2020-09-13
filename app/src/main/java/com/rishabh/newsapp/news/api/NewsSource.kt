package com.rishabh.newsapp.news.api

import com.google.gson.annotations.SerializedName

data class NewsSource(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("country")
    val country: String? = null,
)