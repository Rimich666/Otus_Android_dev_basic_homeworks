package com.example.helloworld

import android.content.Context
import android.util.JsonReader
import java.io.StringReader

data class NewItem(val name: String, val description: String, val pictures: String)

fun getRes(cont: Context)
{
    val list = mutableListOf<NewItem>()
    val map = mutableMapOf<String, String>()
    val reader = JsonReader(StringReader(cont.resources.getString(R.string.movies)))
    reader.beginArray()
    while (reader.hasNext())
    {
        reader.beginObject()
        while (reader.hasNext()) map[reader.nextName()] = reader.nextString()
        println(map)
        reader.endObject()
    }
    list.add(NewItem(map["name"] as String, map["description"] as String, map["pictures"] as String))
    reader.endArray()
    list.forEach{ println(it) }
}