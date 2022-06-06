package com.moviesearch.viewmodel

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesearch.UI.NewItem
import java.io.StringReader

class ListViewModel: ViewModel() {
    var movieList: MutableLiveData<List<NewItem>> = MutableLiveData()
    val repository = com.moviesearch.repository.repository()
    init {
        movieList.value = repository.getMovieList()
    }
    fun getList(){

    }

}

class Items() {
    private var _list: MutableList<NewItem> = mutableListOf()

    constructor(json: String) : this() {
        val map = mutableMapOf<String, String>()
        val reader = JsonReader(StringReader(json))
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) map[reader.nextName()] = reader.nextString()
            reader.endObject()
            Log.d("NewItem", "----------------------------")
            Log.d("NewItem", "Вот что: ${map.toString()}")
            _list.add(
                NewItem(map, false, false)
            )
        }
        reader.endArray()
    }

    constructor(bundle: Bundle) : this() {
        var index: Int = 0
        var itbndl: Bundle?
        do {
            itbndl = bundle.getBundle(index.toString())
            if (itbndl == null) break
            _list.add(NewItem(itbndl as Bundle))
            index++
        } while (true)
    }

    val list: List<NewItem>
        get() = _list

    val bundle: Bundle
        get() {
            val retBundle: Bundle = bundleOf()
            _list.forEachIndexed { index, newItem ->
                retBundle.putBundle(
                    index.toString(),
                    newItem.bundle
                )
            }
            return retBundle
        }

    fun add(item: NewItem) {
        _list.add(item)
    }

    fun remove(position: Int) {
        _list.removeAt(position)
    }

    fun select(list: ArrayList<Int>): Items {
        var ret = Items()
        list.forEach { it -> ret.add(this[it]) }
        return ret
    }

    operator fun get(i: Int): NewItem {
        return _list[i]
    }

    operator fun invoke() = _list

    override fun toString(): String {
        var str: String = ""
        _list.forEach { newItem -> str += "\n${newItem.name} liked= ${newItem.liked}" }
        return str
    }
}