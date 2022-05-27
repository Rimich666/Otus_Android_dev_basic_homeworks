package com.example.moviesearch

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.core.os.bundleOf
import java.io.StringReader

data class NewItem(val name: String, val description: String, val pictures: String, var Selected: Boolean)
{
    constructor(itmap:Map<String,String>, Selected: Boolean):this(
        itmap["name"] as String,
        itmap["description"] as String,
        itmap["pictures"] as String,
        Selected)

    constructor(bundle: Bundle):this(
        bundle["name"] as String,
        bundle["description"] as String,
        bundle["pictures"] as String,
        bundle["selected"] as Boolean)

    val bundle: Bundle
        get() = bundleOf(
            "name" to name,
            "description" to description,
            "pictures" to pictures,
            "selected" to Selected
        )

    val map: Map<String, *>
        get() = mapOf("name" to name,
            "description" to description,
            "pictures" to pictures,
            "Selected" to Selected)
}

class Items()
{
    private var _list: MutableList<NewItem> = mutableListOf()
    constructor(json:String):this()
    {
        val map = mutableMapOf<String, String>()
        val reader = JsonReader(StringReader(json))
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) map[reader.nextName()] = reader.nextString()
            reader.endObject()
            _list.add(NewItem(map, false)
            )
        }
        reader.endArray()
    }

    constructor(bundle: Bundle):this(){
        var index: Int = 0
        var itbndl: Bundle?
        do {
            itbndl = bundle.getBundle(index.toString())
            if (itbndl==null)break
            Log.d("Items from bundle", "index = $index bundle = ${itbndl.toString()}")
            _list.add(NewItem(itbndl as Bundle))
            index++
        }while(true)
    }

    val list: List<NewItem>
        get() = _list

    val bundle: Bundle
        get(){
            val retBundle: Bundle = bundleOf()
            //val listItem: MutableList<Map<String,*>> = mutableListOf()
            _list.forEachIndexed{ index, newItem -> retBundle.putBundle(index.toString(),newItem.bundle) }
            return retBundle
        }
    fun add(item: NewItem)
    {
        _list.add(item)
    }

    override fun toString(): String {
        var str:String = ""
        _list.forEach{newItem ->  str += "\n${newItem.name}"}
        return str
    }
//

}