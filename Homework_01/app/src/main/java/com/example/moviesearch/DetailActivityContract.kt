package com.example.moviesearch

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class DetailActivityContract : ActivityResultContract<String, String>(){

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, DetailActivity::class.java).putExtra("item", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        TODO("Not yet implemented")
    }

}