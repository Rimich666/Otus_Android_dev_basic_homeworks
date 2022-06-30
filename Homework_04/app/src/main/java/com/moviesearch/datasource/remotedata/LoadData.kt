package com.moviesearch.datasource.remotedata

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.io.StringReader
import com.moviesearch.trace
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import java.lang.Exception

object LoadData {
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private const val URL = "https://api.kinopoisk.dev/movie"//"https://api.kinopoisk.dev/movie"
    private const val TOKEN = "MKRJKN4-Q0B463J-J85RBPK-ENWYABY"
    private var limit = 50

    suspend fun loadPages(pages: List<Int>,
                          updateResults: suspend (msg: MutableList<MutableMap<String,Any>>) -> Unit
    ) = coroutineScope {
        val recC = pages.size * limit
        val chProgress = Channel<MutableMap<String, Any>>()
        Log.d("start", "${trace()} max = $recC")

        updateResults(mutableListOf(mutableMapOf("max" to recC)))//, "complete" to false))
        val listsOfPage = mutableMapOf<Int, MutableList<MutableMap<String, Any>>>()

        for (page in pages) {
            listsOfPage[page] = mutableListOf()
            launch {
                val json: String? = request(
                    mapOf("page" to page.toString(),
                        "limit" to limit.toString()),
                    chProgress
                )
                if (json != null) toBase(json, chProgress, page)
            }
        }

        repeat(recC + pages.size * 2){
            val item = chProgress.receive()
            when{
                item.containsKey("pages") ->{
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

        pars.forEach{ (key, value) -> if(key == "search") {
                Log.d("request", "${trace()} Это всё таки мап: ${value is Map<*, *>}")
                val search = value as Map<*,*>
                search.forEach{ (k, v) -> urlBuilder.addQueryParameter("search", v.toString())
                    urlBuilder.addQueryParameter("field", k.toString())}
            }
                else urlBuilder.addQueryParameter(key, value.toString())
        }
        val url: String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).build()
        var returned: String?
        var codeResponse: Int = 666
        var successful = false
        try {
            okHttpClient.newCall(request).execute().use { response ->
                Log.d("request", "${trace()} Код респонса: ${response.code} ${response.message}")
                codeResponse = response.code
                if (!response.isSuccessful){
                    returned = null
                    throw IOException("непонятки какие-то с кодом ${response.code}")
                }
                returned = response.body!!.string()
            }
        }
        catch(e: Exception){
            returned = null
        }
        channel?.send(mutableMapOf(
            "codeResponse" to codeResponse,
            "requestedPage" to pars["page"]!!,
            "successful" to successful))
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
