package com.moviesearch.repository

import android.content.Context
import android.util.Log
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import com.google.common.util.concurrent.ListenableFuture
import com.moviesearch.App.Companion.db
import com.moviesearch.Keys
import com.moviesearch.Keys.progress
import com.moviesearch.WMTAG
import com.moviesearch.datasource.database.DeferredFilm
import com.moviesearch.ui.NewItem
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.database.Film
import com.moviesearch.datasource.database.QueryDb
import com.moviesearch.datasource.database.QueryDb.insertFilms
import com.moviesearch.datasource.remotedata.LoadData
import com.moviesearch.parseJson
import com.moviesearch.trace
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

object Repository {
    private var pagesCount: Int = 0
    private const val SIZEOF = 5
    private var progressI: Int = 0

    suspend fun getPage(page: Int): MutableList<Film>? {
        if (page < 1 || page > pagesCount) return null
        CoroutineScope(Dispatchers.Default).launch(Dispatchers.IO){
            var pages = mutableListOf<Int>()
            for(i in page - SIZEOF .. page + SIZEOF) if (!(i < 1 || i > pagesCount)) pages.add(i)
            pages = QueryDb.checkPages(pages)
            LoadData.loadPages(pages){msg ->
                if (msg[0].containsKey(Keys.pages))
                    launch { insertFilms(msg, null) }
                }
        }
        val res = QueryDb.getPage(page)
        if (res == null)
            Log.d("scrolling", "${trace()} нет страницы $page")
        else
            Log.d("scrolling", "${trace()} есть страница $page")
        return QueryDb.getPage(page)
    }

    private var currentPage: Int = 1
    private var pages: MutableList<Int>? = null

    suspend fun initData(progress: (msg: MutableList<MutableMap<String,Any>>)->Unit) =
        coroutineScope{
        var replay: Boolean = true
        if (pages == null){
            replay = false
            pages = mutableListOf()
            for(i in currentPage .. SIZEOF + 1 ) pages!!.add(i)
            withContext(Dispatchers.Main) {
                progress(mutableListOf(mutableMapOf(Keys.requested to pages!!))) }
        }
        var successful = true
        var responseCount = 0
        val responseTotal = pages!!.size

        launch(Dispatchers.IO) {
            LoadData.loadPages(pages!!) { msg ->
                var channel: Channel<Long>? = null
                when{
                    msg[0].containsKey(Keys.pages) -> {
                        channel = Channel(Channel.RENDEZVOUS)
                        launch { insertFilms(msg, channel) }
                        if (msg[0]["page"] == currentPage) {
                            pagesCount = msg[0][Keys.pages] as Int
                            withContext(Dispatchers.Main) { progress(msg) }
                        }
                    }
                    msg[0].containsKey(Keys.codeResponse) ->{
                        responseCount++
                        val succ = msg[0][Keys.successful] as Boolean
                        successful = successful && succ
                        withContext(Dispatchers.Main) { progress(msg) }
                        if (succ) pages!!.remove(msg[0][Keys.requestedPage].toString().toInt())
                        if (responseCount == responseTotal)
                            withContext(Dispatchers.Main) {
                                progress(mutableListOf(mutableMapOf(Keys.complete to successful)))
                            }
                    }
                    msg[0].containsKey(Keys.max) ->{
                        if (!replay) withContext(Dispatchers.Main) { progress(msg) }
                    }
                    else -> withContext(Dispatchers.Main) { progress(msg) }
                }
                if (channel != null)
                    repeat(msg.size - 1) {
                        val inc = channel.receive()
                        progressI++
                        withContext(Dispatchers.Main) {
                            progress(mutableListOf(mutableMapOf(Keys.progress to progressI)))
                        }
                    }
            }
        }
        if (!replay){
            val favour: Deferred<MutableList<Favourite>> = async{ db?.filmDao()?.getFavourites()!!}
            progress(mutableListOf(mutableMapOf(Keys.favour to favour.await())))
        }
    }

    val json =
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




    suspend fun getDetails(id: Int, takeDetails: (msg: String)->Unit) = coroutineScope{
        launch(Dispatchers.IO) {
            LoadData.getDetail(id){msg ->
                withContext(Dispatchers.Main){
                    takeDetails(msg)
                }
            }
        }
    }

    suspend fun insertWork(item: NewItem) = coroutineScope{
        launch{ db?.filmDao()?.insertDeferred(DeferredFilm(item)) }
    }

    suspend fun removeDeferred(idKp: Int) = coroutineScope{
        launch { db?.filmDao()?.deleteDeferred(idKp)}
    }

    suspend fun getDeferred(idWork: String): DeferredFilm = coroutineScope{
        val res: Deferred<DeferredFilm> = async { db?.filmDao()!!.getDeferredByIdWork(idWork) }
        return@coroutineScope res.await()
    }

    suspend fun dislike(item: NewItem):Boolean = coroutineScope{
        val res: Deferred<Int?> = async { db?.filmDao()?.deleteFavouriteIdKp(item.idKp) }
        return@coroutineScope res.await()!! > 0L
    }

    suspend fun like(item: NewItem): Boolean = coroutineScope{
        val res: Deferred<Long?> = async { db?.filmDao()?.insertFavourite(Favourite(item)) }
        return@coroutineScope res.await()!=0L
    }

}