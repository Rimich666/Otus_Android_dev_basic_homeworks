package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    var resultBun: Bundle = Bundle()
    private val chLike: CheckBox by lazy{ findViewById(R.id.like_chek) }
    private val edComm: EditText by lazy{ findViewById<EditText>(R.id.comment_edit) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val ivDetail = findViewById<ImageView>(R.id.imageDetail)
        val tvName = findViewById<TextView>(R.id.textNameDetail)
        val tvDesc = findViewById<TextView>(R.id.text_short_description_detail)

        ivDetail.setImageResource(resources.getIdentifier(intent.extras?.get("pictures").toString(),"drawable", packageName))
        tvName.text = intent.extras?.get("name").toString()
        tvDesc.text = intent.extras?.get("description").toString()
        resultBun.putBoolean("like", false)
        resultBun.putString("comment", "Не нравится")
    }


    override fun onBackPressed() {
        resultBun.putBoolean("like", chLike.isChecked)
        if (chLike.isChecked) resultBun.putString("comment", edComm.text.toString())
        val result = Intent().putExtra("return", resultBun)
        setResult(RESULT_OK, result)
        super.onBackPressed()
    }
}