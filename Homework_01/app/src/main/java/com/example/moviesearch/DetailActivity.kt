package com.example.moviesearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onBackPressed() {
        Log.d("tracing","onBackPressed ------------------------------------------")
        super.onBackPressed()

    }
}