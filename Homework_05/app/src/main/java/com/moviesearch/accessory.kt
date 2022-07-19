package com.moviesearch

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import java.io.StringReader

const val WMTAG = "postViewing"
const val REQUEST_TITLE = "Запрос страницы"
const val NOTIFICATION_CHANNEL_DESCRIPTION = "App notification channel"

fun trace(): String{
    return "${Throwable().stackTrace[1].fileName}: ${Throwable().stackTrace[1].lineNumber}: "
}

fun trace(all: Boolean): String{
    return "${Throwable().stackTrace.size}:"
}

object Keys{
    const val codeResponse = "codeResponse"
    const val requestedPage = "requestedPage"
    const val successful = "successful"
    const val requested = "requested"
    const val complete = "complete"

    const val pages = "pages"
    const val favour = "favour"
    const val deferred = "deferred"
    const val max = "max"

    const val progress = "progress"
}

const val JSON_CURVE = "Кривой JSON подсунули"

var level = 0
var path = ""

inline fun <reified T> parseJson(json: String): T{
    val reader = JsonReader(StringReader(json))
    return when (reader.peek()){
        JsonToken.BEGIN_OBJECT -> readObject(reader) as T
        JsonToken.BEGIN_ARRAY -> readList(reader) as T
        else -> JSON_CURVE as T
    }
}

fun readList(reader: JsonReader): MutableList<Any>{
    val list = mutableListOf<Any>()
    val root = path
    path = "$root["
    level ++
    Log.d("json", "level = $level")
    reader.beginArray()
    while (reader.hasNext()){
        when (reader.peek()){
            JsonToken.STRING -> {
                list.add((reader.nextString()))
            }
            JsonToken.BEGIN_ARRAY -> {list.add(readList(reader))}
            JsonToken.BEGIN_OBJECT -> {list.add(readObject(reader))}
            //JsonToken.END_ARRAY -> {}
            //JsonToken.END_OBJECT -> {}
            //JsonToken.NAME -> {}
            JsonToken.NUMBER -> {
                val value = reader.nextString()
                list.add(value)
            }
            JsonToken.NULL -> {reader.skipValue()}
            JsonToken.BOOLEAN -> {list.add(reader.nextBoolean())}
            //JsonToken.END_DOCUMENT -> {}
            else -> {throw Exception( JSON_CURVE )}
        }
        Log.d("json", "${trace()} path = $path")
    }
    reader.endArray()
    path = root
    level --
    return list
}

fun readObject(reader: JsonReader): MutableMap<String, Any>{
    val map: MutableMap<String, Any> = mutableMapOf()
    val root = path
    level ++
    Log.d("json", "level = $level")
    reader.beginObject()
    var name = ""
    while (reader.hasNext()){
        when (reader.peek()){
            JsonToken.STRING -> {map[name] = reader.nextString()}
            JsonToken.BEGIN_ARRAY -> {map[name] = readList(reader)}
            JsonToken.BEGIN_OBJECT -> {map[name] = readObject(reader)}
            //JsonToken.END_ARRAY -> {}
            //JsonToken.END_OBJECT -> {}
            JsonToken.NAME -> {
                name = reader.nextName()
                path = "$root.$name"}
            JsonToken.NUMBER -> {
                Log.d("json", "${trace()} =========================================================================================== ")
                val value = reader.nextString()
                map[name] = value
                Log.d("json", "${trace()} number value = $value")
            }
            JsonToken.NULL -> {reader.skipValue()}
            JsonToken.BOOLEAN -> {map[name] = reader.nextBoolean()}
            //JsonToken.END_DOCUMENT -> {}
            else -> {throw Exception( JSON_CURVE )}
        }
        Log.d("json", "${trace()} path = $path")
    }
    reader.endObject()
    path = root
    level --
    return map
}