package com.rishabh.newsapp.core.ui.base

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

typealias BaseActivity = DaggerActivity

@AndroidEntryPoint
abstract class DaggerActivity : AppCompatActivity()
