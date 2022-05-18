package com.example.helloworld

import android.content.res.Resources
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener{ setImage() }
    }

    private fun setImage(){
        Log.d("tag", "setImage")
        val img = findViewById<ImageView>(R.id.imageView)
        val drawableName : String = "potter"
        val resID : Int = resources.getIdentifier(drawableName, "drawable", packageName)
        Log.d("tag", packageName)
        Log.d("tag", resID.toString())
        img.setImageResource(resID)

        val jsonStr: String = resources.getString(R.string.movies)
        Log.d("jsonStr", jsonStr)
        val stream: StringReader = StringReader(jsonStr)
        Log.d("read json", "StringReader")
        val reader: JsonReader = JsonReader(stream)
        Log.d("read json", "JsonReader: ${reader.toString()}")

        reader.beginArray()
        Log.d("read json", "beginArray()")
        while (reader.hasNext())
        {
            Log.d("read json", "next object")
            reader.beginObject()
            Log.d("read json", "beginObject()")
            var name: String
            var value: String
            while (reader.hasNext())
            {
                Log.d("read json", "want name")
                name = reader.nextName()
                value = reader.nextString()
                println("$name : $value")
            }
            print("\n")
            reader.endObject()
        //Log.d("Object",reader.nextString())
        }
        reader.endArray()

    }
}