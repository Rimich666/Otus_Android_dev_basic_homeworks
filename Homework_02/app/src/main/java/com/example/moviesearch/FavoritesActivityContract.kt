package com.example.moviesearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract

class FavoritesActivityContract : ActivityResultContract<Bundle, String>() {


    override fun createIntent(context: Context, input: Bundle): Intent {
        return Intent(context, FavoritesActivity::class.java).putExtras(input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?):String {
        if (resultCode == Activity.RESULT_OK){
            Log.d("FavoritesContract", intent?.getStringExtra("msg") as String)
        }
        return intent?.getStringExtra("msg") as String
    }
}