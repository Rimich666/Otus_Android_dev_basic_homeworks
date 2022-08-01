package com.moviesearch

import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.readList
import io.reactivex.rxjava3.core.Emitter
import java.io.StringReader
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.kotlinProperty

const val WMTAG = "postViewing"
const val JSTAG = "json"
const val FTTAG = "firebaseToken"
const val RCTAG = "RemoteConfigUtils"
const val RXTAG = "RXJava"

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
    const val limit = "limit"
    const val page = "page"

    const val progress = "progress"
}

const val JSON_CURVE = "Кривой JSON подсунули"

var level = 0
var path = ""

annotation class Path(val path: String)
annotation class MyObject()


class Prop(val property: KMutableProperty<*>){
    lateinit var action: () -> Any
    lateinit var type: KClass<*>
}

const val TAG = "Generic"

private fun fillProperties(jC: Class<*>, properties: MutableMap<String, Prop>, reader: JsonReader){
    var classArg: Class<*>
    jC.declaredFields.forEach {
        if (!it.kotlinProperty!!.hasAnnotation<Path>()) return@forEach
        val genericType = it.genericType
        var action: () -> Any = {}
        classArg = if (genericType is ParameterizedType){
            Class.forName(genericType.actualTypeArguments[0].typeName)
        } else{
            it.type
        }
        if (classArg.isAnnotationPresent(MyObject::class.java))
            fillProperties(classArg, properties, reader)
        else
            Log.d(TAG, "${trace()} ${it.name} classArg: ${classArg.kotlin}")
            action = when (classArg.kotlin) {
                Int::class -> {
                    { reader.nextInt() }
                }
                Double::class -> {
                    { reader.nextDouble() }
                }
                Long::class -> {
                    { reader.nextLong() }
                }
                Boolean::class -> {
                    { reader.nextBoolean() }
                }
                else -> {
                    { reader.nextString() }
                }
            }
        properties[it.kotlinProperty!!.findAnnotation<Path>()!!.path] =
            Prop(it.kotlinProperty as KMutableProperty<*>).apply {
                this.action = action
                this.type = classArg.kotlin
            }
    }

}

private const val VOW =
"""
Торжественно клянусь причесать этот сумбур... скоро ...
в этом году
"""

fun parseJson(json: String, obj: Any, emitter: Emitter<Any>){
    val reader = JsonReader(StringReader(json))
    val properties = mutableMapOf<String, Prop>()
    fillProperties(obj.javaClass, properties, reader)
    properties.forEach {
        Log.d(TAG, "${trace()} property: $it")
    }

    when (reader.peek()){
        JsonToken.BEGIN_OBJECT -> readObject(reader, obj, properties)
        JsonToken.BEGIN_ARRAY -> readList(reader, obj, properties)
        else -> JSON_CURVE
    }
    Log.d(TAG, "${trace()} obj = ${obj.toString()}")
}

fun parseJson(json: String, obj: Any): Any {
    val reader = JsonReader(StringReader(json))
    val type = obj::class
    val properties = mutableMapOf<String, Prop>()
    fillProperties(obj.javaClass, properties, reader)
    properties.forEach {
        Log.d(TAG, "${trace()} property: $it")
    }

    when (reader.peek()){
        JsonToken.BEGIN_OBJECT -> readObject(reader, obj, properties)
        JsonToken.BEGIN_ARRAY -> readList(reader, obj, properties)
        else -> JSON_CURVE
    }
    Log.d(TAG, "${trace()} obj = ${obj.toString()}")
    return obj
}

private fun readList(reader: JsonReader, obj: Any, prop: MutableMap<String, Prop>){
    val root = path

    path = "$root["
    Log.d(TAG, "${trace()} path = $path")
    level ++
    reader.beginArray()
    while (reader.hasNext()){
        if (path in prop.keys && prop[path]!!.property.returnType.classifier == MutableList::class){
            val list = prop[path]!!.property.getter.call(obj) as MutableList<Any>
            when (reader.peek()){
                JsonToken.STRING -> {list.add(reader.nextString())}
                JsonToken.BEGIN_ARRAY -> {readList(reader, obj, prop)}
                JsonToken.BEGIN_OBJECT -> {
                    val inst = prop[path]!!.type.createInstance()
                    Log.d(TAG, "${trace()} inst = $inst")
                    readObject(reader, inst, prop)
                    list.add(inst)
                }
                JsonToken.NUMBER -> {list.add(reader.nextString())}
                JsonToken.NULL -> {reader.skipValue()}
                JsonToken.BOOLEAN -> {reader.nextBoolean()}
                else -> {throw Exception( JSON_CURVE )}
            }
        }
        else{
            when (reader.peek()){
                JsonToken.STRING -> {reader.nextString()}
                JsonToken.BEGIN_ARRAY -> {readList(reader, obj, prop)}
                JsonToken.BEGIN_OBJECT -> {readObject(reader, obj, prop)}
                JsonToken.NUMBER -> {reader.nextString()}
                JsonToken.NULL -> {reader.skipValue()}
                JsonToken.BOOLEAN -> {reader.nextBoolean()}
                else -> {throw Exception( JSON_CURVE )}
            }
        }
    }
    reader.endArray()
    path = root
    level --
}

private  fun readObject(reader: JsonReader, obj: Any, prop: MutableMap<String, Prop>){
    val root = path
    level ++
    reader.beginObject()
    var name = ""
    while (reader.hasNext()){
        when (reader.peek()){
            JsonToken.BEGIN_ARRAY -> {readList(reader, obj, prop)}
            JsonToken.BEGIN_OBJECT -> {readObject(reader, obj, prop)}
            JsonToken.NAME -> {
                name = reader.nextName()
                path = "$root.$name"
                Log.d(TAG, "${trace()} path = $path")
            }
            else -> {
                if (path in prop.keys){
                    if (reader.peek() == JsonToken.NULL) reader.skipValue()
                    else {
                        Log.d(TAG, "${trace()} action = ${prop[path]!!.action}")
                        prop[path]!!.property.setter.call(obj, prop[path]!!.action())
                    }
                }
                else
                    reader.skipValue()
            }
        }
    }
    reader.endObject()
    path = root
    level --
}

fun readList(reader: JsonReader): MutableList<Any>{
    val list = mutableListOf<Any>()
    reader.beginArray()
    while (reader.hasNext()){
        when (reader.peek()){
            JsonToken.STRING -> {
                list.add((reader.nextString()))
            }
            JsonToken.BEGIN_ARRAY -> {list.add(readList(reader))}
            JsonToken.BEGIN_OBJECT -> {list.add(readObject(reader))}
            JsonToken.NUMBER -> {
                val value = reader.nextString()
                list.add(value)
            }
            JsonToken.NULL -> {reader.skipValue()}
            JsonToken.BOOLEAN -> {list.add(reader.nextBoolean())}
            else -> {throw Exception( JSON_CURVE )}
        }
    }
    reader.endArray()
    return list
}

fun readObject(reader: JsonReader): MutableMap<String, Any>{
    val map: MutableMap<String, Any> = mutableMapOf()
    reader.beginObject()
    var name = ""
    while (reader.hasNext()){
        when (reader.peek()){
            JsonToken.STRING -> {map[name] = reader.nextString()}
            JsonToken.BEGIN_ARRAY -> {map[name] = readList(reader)}
            JsonToken.BEGIN_OBJECT -> {map[name] = readObject(reader)}
            JsonToken.NAME -> { name = reader.nextName() }
            JsonToken.NUMBER -> {
                map[name] = reader.nextString()
            }
            JsonToken.NULL -> {reader.skipValue()}
            JsonToken.BOOLEAN -> {map[name] = reader.nextBoolean()}
            else -> {throw Exception( JSON_CURVE )}
        }
        Log.d("json", "${trace()} path = $path")
    }
    reader.endObject()
    return map
}

private val json =
    """    
    {
        "externalId":{
            "_id":"6248e7acbf55de69b8bc1c8f","imdb":"tt7403574"
            },
        "poster":{
            "_id":"6248e7acbf55de69b8bc1c91",
            "url":"https://st.kp.yandex.net/images/film_big/986638.jpg",
            "previewUrl":"https://st.kp.yandex.net/images/film_iphone/iphone360_986638.jpg"
            },
        "backdrop":{
            "url":"https://www.themoviedb.org/t/p/original/2RAphq12UvNukT2tivP0YfOimwh.jpg",
            "previewUrl":"https://www.themoviedb.org/t/p/w500/2RAphq12UvNukT2tivP0YfOimwh.jpg"
            },
        "rating":{
            "_id":"6248e7acbf55de69b8bc1c92",
            "kp":6,
            "imdb":4,
            "filmCritics":0,
            "russianFilmCritics":0,
            "await":52.44
            },
        "votes":{
            "_id":"6248e7acbf55de69b8bc1c93",
            "kp":12571,
            "imdb":307,
            "filmCritics":0,
            "russianFilmCritics":0,
            "await":328
            },
        "videos":{
            "_id":"62c7603d8e09a010ee087972",
            "trailers":[
                {
                    "_id":"62c7603d8e09a010ee087973",
                    "url":"http://trailers.s3.mds.yandex.net/video_original/150909-1a3e35f31e2403af0d1e5adc359dc4aa",
                    "name":"Трейлер",
                    "site":"unknown"
                    }
                ],
            "teasers":[]
            },
        "budget":{
            "_id":"62c7603d8e09a010ee087956",
            "value":0,
            "currency":"$"
            },
        "fees":{
            "world":{
                "_id":"62c7603d8e09a010ee08795b",
                "value":1108819,
                "currency":"$"
                },
            "_id":"62c7603d8e09a010ee08795a"
            },
        "distributors":{
            "distributor":"Каропрокат",
            "distributorRelease":null
            },
        "premiere":{
            "_id":"62c7603d8e09a010ee087958",
            "country":"Чили",
            "world":"2018-08-30T00:00:00.000Z",
            "russia":"2018-08-23T00:00:00.000Z",
            "cinema":"2018-08-23T00:00:00.000Z"
            },
        "images":{
            "framesCount":31
            },
        "collections":[],
        "updateDates":[
            "2021-09-25T19:06:12.000Z",
            "2021-09-25T19:51:30.000Z",
            "2021-09-27T04:08:43.000Z",
            "2021-10-03T00:07:45.000Z",
            "2021-10-23T20:52:09.000Z"
            ],
        "status":"Выпущен",
        "movieLength":70,
        "productionCompanies":[
            {
                "name":"Licensing Brands",
                "url":null,"previewUrl":null
                }
            ],
        "spokenLanguages":[
            {
                "name":"Pусский",
                "nameEn":"Russian"
                }
            ],
        "id":986638,
        "type":"carton",
        "name":"Принцесса и дракон",
        "description":"Мечтали ли вы когда-нибудь о том, чтобыпопасть в сказку? Принцесса Варвара находит в библиотеке замка магическую книгу и с ее помощью оказывается в волшебной стране, где встречает фантастических животных, веселых гномов и настоящего дракона.",
        "slogan":null,
        "year":2018,
        "facts":[
            {
                "value":"В прическе Варвары 3 миллиона волосинок.",
                "type":"fact",
                "spoiler":false
                },
            {
                "value":"В мультфильме 50 персонажей и 20 уникальных локаций.",
                "type":"fact","spoiler":false
                },
            {
                "value":"На работу ушло больше 1000 дней.",
                "type":"fact","spoiler":false
                }
            ],
            "genres":[
                {
                    "name":"фэнтези"
                    },
                {
                    "name":"мультфильм"
                    },
                {
                    "name":"детский"
                    }
                ],
            "countries":[
                {
                    "name":"Россия"
                    }
                ],
            "seasonsInfo":[],
            "persons":[
                {
                    "id":2309894,
                    "name":"Марина Нефедова",
                    "enName":"",
                    "description":null,
                    "enProfession":"director",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_2309894.jpg"
                    },
                {
                    "id":1414025,
                    "name":"Ани Лорак",
                    "enName":"",
                    "description":"Варвара, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1414025.jpg"
                    },
                {
                    "id":1929007,
                    "name":"Ирина Киреева",
                    "enName":"",
                    "description":"Королева, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1929007.jpg"
                    },
                {
                    "id":2340955,
                    "name":"Сергей Смирнов",
                    "enName":"",
                    "description":"Дракоша, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_2340955.jpg"
                    },
                {
                    "id":1078858,
                    "name":"Василий Дахненко",
                    "enName":"",
                    "description":"Дядя Дра / Дядя Юлиус / рассказчик, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1078858.jpg"
                    },
                {
                    "id":1781077,
                    "name":"Диомид Виноградов",
                    "enName":"",
                    "description":"Король, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1781077.jpg"
                    },
                {
                    "id":5289720,
                    "name":"Константин Кожевников",
                    "enName":"",
                    "description":"Бальтазар, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_5289720.jpg"
                    },
                {
                    "id":1963412,
                    "name":"Данил Щебланов",
                    "enName":"",
                    "description":"шут Арри / говорящая дверь, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1963412.jpg"
                    },
                {
                    "id":1643982,
                    "name":"Лина Иванова",
                    "enName":"",
                    "description":"гном Умник, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1643982.jpg"
                    },
                {
                    "id":1078859,
                    "name":"Элиза Мартиросова",
                    "enName":"",
                    "description":"гном Весельчак, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1078859.jpg"
                    },
                {
                    "id":1893992,
                    "name":"Варвара Чабан",
                    "enName":"",
                    "description":"гном Молчун, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1893992.jpg"
                    },
                {
                    "id":1649302,
                    "name":"Екатерина Виноградова",
                    "enName":"",
                    "description":"гном Шустрик, озвучка",
                    "enProfession":"actor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_1649302.jpg"
                    },
                {
                    "id":555304,
                    "name":"Роман Борисевич",
                    "enName":"",
                    "description":null,
                    "enProfession":"producer",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_555304.jpg"
                    },
                {
                    "id":2954246,
                    "name":"Василий Ровенский",
                    "enName":"",
                    "description":null,
                    "enProfession":"producer",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_2954246.jpg"
                    },
                {
                    "id":2954246,
                    "name":"Василий Ровенский",
                    "enName":"",
                    "description":null,
                    "enProfession":"writer",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_2954246.jpg"
                    },
                {
                    "id":2231224,
                    "name":"Антон Грызлов",
                    "enName":"","description":null,
                    "enProfession":"composer",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_2231224.jpg"
                    },
                {
                    "id":6874936,
                    "name":"Лела Хитарова",
                    "enName":"",
                    "description":null,
                    "enProfession":"editor",
                    "photo":"https://st.kp.yandex.net/images/actor_iphone/iphone360_6874936.jpg"
                    }
                ],
            "lists":[],
            "typeNumber":null,
            "alternativeName":null,
            "enName":null,
            "names":[
                {
                    "name":"Принцесса и дракон"
                    }
                ],
            "ageRating":6,
            "ratingMpaa":null,
            "updatedAt":"2022-07-07T22:37:49.868Z",
            "similarMovies":[],
            "imagesInfo":{
                "_id":"621b6291651ebcb9dfa3c98d",
                "framesCount":31
            },
        "shortDescription":"Книга переносит девочку в мир магии. Озорной мультфильм о том, как любовь к чтению превращает жизнь в сказку",
        "technology":{
            "_id":"6248e7acbf55de69b8bc1c94","hasImax":false,"has3D":false
            },
        "ticketsOnSale":false,
        "sequelsAndPrequels":[],
        "createDate":"2022-07-17T14:36:50.333Z"
        }
    
"""