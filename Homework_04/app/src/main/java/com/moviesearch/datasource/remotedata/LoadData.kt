package com.moviesearch.datasource.remotedata

import android.content.Context
import android.telecom.Call
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
import com.moviesearch.App
import com.moviesearch.datasource.database.DbObject
import com.moviesearch.datasource.database.DbObject.getDatabase
import com.moviesearch.datasource.database.FilmDb
import com.moviesearch.datasource.remotedata.LoadData.limit
import com.moviesearch.datasource.remotedata.LoadData.page
import com.moviesearch.datasource.remotedata.LoadData.token
import java.util.concurrent.Executors

object LoadData {
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private val URL = "https://api.kinopoisk.dev/movie"
    private var page: Int = 2
    private var pagesCount: Int = 1
    private val token = "MKRJKN4-Q0B463J-J85RBPK-ENWYABY"
    private var limit = 50

    private var db: FilmDb? = null
    fun initBase(progress: (String) -> Unit){
        Executors.newSingleThreadExecutor().execute(
            Runnable{
                Log.d("TraceOfBase", "Собираюсь запросить базу")
                db = App.getDatabase()
                Log.d("TraceOfBase", "И что собсно получил? ${db.toString()}")

            }
        )
        GlobalScope.launch(Dispatchers.IO){
            startCoroutines{
                msg->withContext(Dispatchers.Main){
                progress(msg)
                }
            }
        }

        progress("Привет из Load data")
    }
    private suspend fun startCoroutines(updateResults: suspend (msg: String) -> Unit
    ) = coroutineScope {
        val channel = Channel<Int>()
        launch {
            coroutineOne(channel)
        }
        launch {
            coroutineTwo(channel)
        }

        repeat(200){
            val i = channel.receive()
            updateResults("шаг $i")

        }
    }

    private suspend fun coroutineOne(channel: Channel<Int>){
        val json: String = request()
        Log.d("response", "-----------request всё")
        toBase(json, channel)

    }

    private suspend fun coroutineTwo(channel: Channel<Int>){
        for(i in 101..150){
            delay(15L)
            channel.send(i)
        }
    }

    private fun request():String {
        val urlBuilder: HttpUrl.Builder =
            URL.toHttpUrlOrNull()?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token", token)
        urlBuilder.addQueryParameter("page", page.toString())
        urlBuilder.addQueryParameter("limit", limit.toString())
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

    private suspend fun toBase(json:String, channel:Channel<Int>){


        val reader = JsonReader(StringReader(json))
        var nm: String
//        Log.d("response", json)
//        Log.d("response", "-----------to Base start")
        reader.beginObject()
        while (reader.hasNext()) {
            nm = reader.nextName()
            if (nm == "docs") {
                var i = 0
                reader.beginArray()
                while (reader.hasNext()) {
                    readObject(reader)
                    Log.d("film_add", itemMap.toString())

                    //db = mApp.getDatabase()
                    //Log.d("TraceOfBase", "И что получил? ${db.toString()}")
                    db?.filmDao()?.insert(Film(itemMap))
                    channel.send(++i)

                }
                reader.endArray()
            }
            else{
                Log.d("response", "$nm : ${reader.nextInt()}")
            }
        }
        reader.endObject()
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

