package com.rishabh.newsapp.news.ui.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rishabh.newsapp.R
import com.rishabh.newsapp.databinding.NewsDetailsFragmentBinding
import com.rishabh.newsapp.news.api.NewsArticle
import com.bumptech.glide.Glide
import com.google.gson.Gson

const val NEWS_MODEL = "news_model"

class NewsDetailsFragment : Fragment() {

    companion object {
        fun newInstance(newsDetailsModel: NewsArticle): NewsDetailsFragment {
            val args = Bundle()
            val gson = Gson()
            args.putString(NEWS_MODEL, gson.toJson(newsDetailsModel))
            val fragment = NewsDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentNewsDetailsBinder = NewsDetailsFragmentBinding
            .inflate(inflater, container, false)
        val gson = Gson()
        val model = gson.fromJson(requireArguments().getString(NEWS_MODEL), NewsArticle::class.java)
        fragmentNewsDetailsBinder.newsModel = model
        fragmentNewsDetailsBinder.newsDetailsImage
        Glide.with(this)
            .load(model.urlToImage)
            .placeholder(R.drawable.tools_placeholder)
            .into(fragmentNewsDetailsBinder.newsDetailsImage)
        return fragmentNewsDetailsBinder.root
    }
}