package com.example.moviesearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract

class DetailActivityContract : ActivityResultContract<Bundle, Bundle?>(){

    override fun createIntent(context: Context, input: Bundle): Intent {
        return Intent(context, DetailActivity::class.java).putExtras(input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        Log.d("DetailActivityContract", "parseResult*********************************")
        if (resultCode == Activity.RESULT_OK){
            val ind = intent?.getBundleExtra("return")
            println("Это кончно результат, но что таки сним делать? ${ind?.getBoolean("like")}  ${ind?.getString("comment")}")
            return intent?.getBundleExtra("return")
        }
        return null
    }

}