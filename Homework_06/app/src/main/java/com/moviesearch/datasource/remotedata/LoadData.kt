package com.moviesearch.datasource.remotedata

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import com.google.android.gms.common.data.DataBufferObserver
import com.moviesearch.*
import com.moviesearch.Keys.max
import com.moviesearch.Keys.page
import com.moviesearch.firebase.RemoteConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.io.StringReader
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class Item(val page: Int){

}

class ResultRequest(val codeResponse: Int, val page: Int, val successful: Boolean){

    override fun toString(): String {
        return "\nResult:\nCode of response = $codeResponse\npage = $page\nsuccessful = $successful"
    }
}

class Progress(val limit: Int, val max: Int)

object LoadData {
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private const val URL = "https://api.kinopoisk.dev/movie"
    private const val TOKEN = "MKRJKN4-Q0B463J-J85RBPK-ENWYABY"
    private var limit = 50

    fun loadPages(pages: List<Int>, emitter: Emitter<Any>){
        Log.d(RXTAG, "pages: $pages")
        emitter.onNext(Progress(limit, pages.size * limit))
        val arr: Array<Observable<Any>> = Array(pages.size) {
            Observable.create<Any> { emitter ->
                val json = request(
                    mapOf(
                        "page" to pages[it],
                        "limit" to limit.toString(),
                        "search" to RemoteConfig.getSearchMap(),
                        "sort" to RemoteConfig.getSortMap()
                    ),
                    emitter
                )
                if (json != null) parseJson(json, Item(pages[it]) ,emitter)
            }
        }
        val observable = Observable.mergeArray(*arr)
        observable.subscribeOn(Schedulers.computation())
        observable.subscribe(object: Observer<Any>{
            override fun onNext(t: Any) {
                Log.d(RXTAG, "${trace()} $t")
                emitter.onNext(t)

            }

            override fun onSubscribe(d: Disposable) {
                //TODO("Not yet implemented")
            }

            override fun onError(e: Throwable) {
                //TODO("Not yet implemented")
            }

            override fun onComplete() {
                //TODO("Not yet implemented")
            }

        })


    }

    private fun request(pars:Map<String,*>, emitter: Emitter<Any>): String? {
        Log.d(RXTAG, "${trace()} request")
        val urlBuilder: HttpUrl.Builder =
            URL.toHttpUrlOrNull()?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token", TOKEN)
        Log.d(RXTAG, "${trace()} $pars")
        pars.forEach{ (key, value) ->
            when(key)
            {
                "search" -> {
                    val search = value as Map<*,*>
                    search.forEach{ (k, v) -> urlBuilder.addQueryParameter("search", v.toString())
                        urlBuilder.addQueryParameter("field", k.toString())}
                }
                "sort" -> {
                    val search = value as Map<*,*>
                    search.forEach{ (k, v) -> urlBuilder.addQueryParameter("sortType", v.toString())
                        urlBuilder.addQueryParameter("sortField", k.toString())}
                }
                else -> urlBuilder.addQueryParameter(key, value.toString())
            }
        }
        val url: String = urlBuilder.build().toString()
        Log.d(RXTAG, "${trace()} url: $url")
        val request: Request = Request.Builder().url(url).build()
        var returned: String?
        var codeResponse: Int = 666
        var successful = false
        try {
            okHttpClient.newCall(request).execute().use { response ->
                codeResponse = response.code
                if (!response.isSuccessful){
                    returned = null
                    throw IOException("непонятки какие-то с кодом ${response.code}")
                }
                successful = true
                returned = response.body!!.string()
            }
        }
        catch(e: Exception){
            returned = null
        }
        emitter.onNext(ResultRequest(codeResponse, pars["page"]!! as Int, successful))
        return returned
    }

    suspend fun loadPages(pages: List<Int>,
                          updateResults: suspend (msg: MutableList<MutableMap<String,Any>>) -> Unit
    ) = coroutineScope {
        val recC = pages.size * limit
        val chProgress = Channel<MutableMap<String, Any>>()
        updateResults(mutableListOf(mutableMapOf(Keys.max to recC, Keys.limit to limit)))//, "complete" to false))
        val listsOfPage = mutableMapOf<Int, MutableList<MutableMap<String, Any>>>()

        for (page in pages) {
            listsOfPage[page] = mutableListOf()
            launch {
                val json: String? = request(
                    mapOf("page" to page.toString(),
                        "limit" to limit.toString(),
                        "search" to RemoteConfig.getSearchMap(),
                        "sort" to RemoteConfig.getSortMap()
                    ),
                    chProgress,
                )
                if (json != null) toBase(json, chProgress, page)
            }
        }

        repeat(recC + pages.size * 2){
            val item = chProgress.receive()
            when{
                item.containsKey(Keys.pages) ->{
                    listsOfPage[item["page"] as Int]!!.add(0, item.toMutableMap())
                    updateResults(listsOfPage[item["page"] as Int]!!)
                }
                item.containsKey("id") -> {
                    listsOfPage[item["page"] as Int]!!.add(item.toMutableMap())
                }
                else -> {updateResults(mutableListOf(item))}
            }
        }
    }

    suspend fun getDetail(id: Int, updateResults: suspend (msg: String) -> Unit
    ) = coroutineScope{
        var json: String = ""
        val jsonDef: Deferred<String?> =
        async { request(mapOf("search" to mapOf<String, String>("id" to id.toString())), null) }
        if (jsonDef.await() != null)
            json = jsonDef.await()!!
        updateResults(json)
    }

    private suspend fun request(pars:Map<String,*>, channel: Channel<MutableMap<String, Any>>?):String? {
        val urlBuilder: HttpUrl.Builder =
            URL.toHttpUrlOrNull()?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token", TOKEN)
        Log.d(RCTAG, "${trace()} $pars")
        pars.forEach{ (key, value) ->
            when(key)
            {
                "search" -> {
                    val search = value as Map<*,*>
                    search.forEach{ (k, v) -> urlBuilder.addQueryParameter("search", v.toString())
                        urlBuilder.addQueryParameter("field", k.toString())}
                }
                "sort" -> {
                    val search = value as Map<*,*>
                    search.forEach{ (k, v) -> urlBuilder.addQueryParameter("sortType", v.toString())
                        urlBuilder.addQueryParameter("sortField", k.toString())}
                }
                else -> urlBuilder.addQueryParameter(key, value.toString())
            }
        }
        val url: String = urlBuilder.build().toString()
        Log.d(RCTAG, "${trace()} url: $url")
        val request: Request = Request.Builder().url(url).build()
        var returned: String?
        var codeResponse: Int = 666
        var successful = false
        try {
            okHttpClient.newCall(request).execute().use { response ->
                codeResponse = response.code
                if (!response.isSuccessful){
                    returned = null
                    throw IOException("непонятки какие-то с кодом ${response.code}")
                }
                successful = true
                returned = response.body!!.string()
            }
        }
        catch(e: Exception){
            returned = null
        }
        channel?.send(mutableMapOf(
            Keys.codeResponse to codeResponse,
            Keys.requestedPage to pars["page"]!!,
            Keys.successful to successful))
        return returned
    }

    private suspend fun toBase(json:String,
                               channel:Channel<MutableMap<String,Any>>,
                               page: Int,
                               ){
        val reader = JsonReader(StringReader(json))
        var nm: String
        val itemMap: MutableMap<String, Any> = mutableMapOf(
            "id" to 0,
            "name" to "",
            "shortDescription" to "",
            "alternativeName" to "",
            "poster" to "",
            "previewUrl" to "",
            "page" to page
        )
        var item = itemMap.toMutableMap()

        reader.beginObject()
        while (reader.hasNext()) {
            nm = reader.nextName()
            when(nm){
                "docs" ->{
                    reader.beginArray()
                    while (reader.hasNext()) {
                        readObject(reader, item)
                        channel.send(item)
                        item = itemMap.toMutableMap()
                        }
                    reader.endArray()
                    }
                "pages" -> {
                    channel.send(mutableMapOf(
                        "pages" to reader.nextInt(),
                        "page" to page))
                    }
                else -> Log.d("response", "${trace()} $nm : ${reader.nextInt()}")
            }
        }
        reader.endObject()
    }

    private fun readObject(reader: JsonReader, itemMap: MutableMap<String, Any>){
        var nm: String
        reader.beginObject()
        while (reader.hasNext()) {
            nm = reader.nextName()
            if(reader.peek() == JsonToken.NULL){
                reader.skipValue()
                continue
            }
            if (itemMap.containsKey(nm)) {
                when (reader.peek()){
                    JsonToken.STRING -> itemMap[nm] = reader.nextString()
                    JsonToken.NUMBER -> itemMap[nm] = reader.nextInt()
                    JsonToken.BEGIN_OBJECT -> readObject(reader, itemMap)
                    JsonToken.END_OBJECT ->{}
                    else -> reader.skipValue()
                }
            }
            else reader.skipValue()
        }
        reader.endObject()
    }
}
