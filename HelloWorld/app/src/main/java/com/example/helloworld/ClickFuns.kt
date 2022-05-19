package com.example.helloworld

import android.content.Context
import android.content.res.Resources
import android.util.JsonReader
import android.util.Log
import java.io.StringReader
import java.security.AccessController.getContext

data class NewItem(val name: String, val description: String, val pictures: String)

fun getRes(cont: Context)
{

    val list = mutableListOf<NewItem>()
    Log.d("Tag", "get resources function")
    val jsonStr: String = cont.resources.getString(R.string.movies)
    Log.d("jsonStr", jsonStr)
    val stream: StringReader = StringReader(jsonStr)
    Log.d("read json", "StringReader")
    val reader: JsonReader = JsonReader(stream)
    Log.d("read json", "JsonReader: ${reader.toString()}")
    //reader.isLenient = true
    reader.beginArray()
    Log.d("read json", "beginArray()")
    var name: String = ""
    var value: String = ""

    while (reader.hasNext())
    {
        //var item: NewItem = NewItem("","","")
        Log.d("read json", "next object")
        reader.beginObject()
        Log.d("read json", "beginObject()")
        while (reader.hasNext())
        {
            Log.d("read json", "want name")
            name = reader.nextName()
            value = reader.nextString()
            when(name){
            "name" -> item.name = value
            }
            println("$name : $value")
        }
        print("\n")
        reader.endObject()
        //Log.d("Object",reader.nextString())
    }
    reader.endArray()
}