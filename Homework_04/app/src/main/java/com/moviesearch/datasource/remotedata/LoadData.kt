package com.moviesearch.datasource.remotedata

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import com.moviesearch.datasource.database.Film
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.io.StringReader
import com.moviesearch.App.Companion.db
import com.moviesearch.datasource.database.Page
import com.moviesearch.trace
import java.lang.Exception

object LoadData {
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private val URL = "https://api.kinopoisk.dev/movie"
    private val token = "MKRJKN4-Q0B463J-J85RBPK-ENWYABY"
    private var limit = 50
    var pagesCount = 0

    suspend fun loadPages(pages: List<Int>, currPage: Int, updateResults: suspend (msg: Map<String,*>) -> Unit
    ) = coroutineScope {
        val recC = pages.size * limit
        var i = 0

        //val chProgress = Channel<Int>()
        val chProgress = Channel<MutableMap<String, Any>?>()
        updateResults(mapOf("max" to recC, "progress" to 0, "complete" to false))

        for (page in pages) {
            launch {
                val json: String = request(mapOf("page" to page.toString(), "limit" to limit.toString()))
                pagesCount = toBase(json, chProgress, page, page == currPage)
            }
        }

        repeat(recC){
            val item = chProgress.receive()
            i ++
            updateResults(mapOf("progress" to i, "complete" to false))
            updateResults(mapOf("item" to item, "complete" to false))
        }
        updateResults(mapOf("complete" to true))
    }

    suspend fun getDetail(id: Int, updateResults: suspend (msg: String) -> Unit
    ) = coroutineScope{
        val json: Deferred<String> =
        async { request(mapOf("search" to mapOf<String, String>("id" to id.toString()))) }
        updateResults(json.await())
    }

    private fun request(pars:Map<String,*>):String {
        val urlBuilder: HttpUrl.Builder =
            URL.toHttpUrlOrNull()?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token", token)
        pars.forEach{
            key, value -> if(key == "search") {
                Log.d("request", "${trace()} Это всё таки мап: ${value is Map<*, *>}")
                val search = value as Map<*,*>
                search.forEach{k, v -> urlBuilder.addQueryParameter("search", v.toString())
                    urlBuilder.addQueryParameter("field", k.toString())}
            }
                else urlBuilder.addQueryParameter(key, value.toString())
        }
        val url: String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("непонятки какие-то $response")
            return response.body!!.string()
        }
    }

    private val itemMap: MutableMap<String, Any> = mutableMapOf(
        "id" to 0,
        "name" to "",
        "shortDescription" to "",
        "alternativeName" to "",
        "poster" to "",
        "previewUrl" to "")

    private suspend fun toBase(json:String,
                               channel:Channel<MutableMap<String,Any>?>,
                               page: Int,
                               needRes: Boolean
                               ): Int{
        val reader = JsonReader(StringReader(json))
        var nm: String
        var film: Film
        var pageId: Long = 0
        var pc = 0
        reader.beginObject()
        while (reader.hasNext()) {
            nm = reader.nextName()
            when(nm){
                "docs" ->{
                    reader.beginArray()
                    //if (reader.peek()!=JsonToken.END_ARRAY) pageId = db?.filmDao()?.insertPage(Page(page)) as Long
                    while (reader.hasNext()) {
                        readObject(reader)
                        itemMap["page"] = page
                        //film = Film(itemMap, pageId)
                        //db?.filmDao()?.insertFilm(film)
                        //if(needRes) channel.send(itemMap)
                        //else channel.send(null)
                        channel.send(itemMap)
                        itemMap.forEach{ (key, value) -> if(value is Int) itemMap[key] = 0 else itemMap[key] = ""}
                    }
                    reader.endArray()
                    }
                "pages" -> pc = reader.nextInt()

                else -> Log.d("response", "$nm : ${reader.nextInt()}")

            }
        }
        reader.endObject()
        return pc
    }

    private fun readObject(reader: JsonReader){
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
                    JsonToken.BEGIN_OBJECT -> readObject(reader)
                    JsonToken.END_OBJECT ->{}
                    else -> reader.skipValue()
                }
            }
            else reader.skipValue()
        }
        reader.endObject()
    }
}
