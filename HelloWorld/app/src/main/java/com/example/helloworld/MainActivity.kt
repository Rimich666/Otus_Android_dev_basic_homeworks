package com.example.helloworld

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.helloworld.database.DbObject
import com.example.helloworld.database.Film
import com.example.helloworld.database.FilmDb
import com.example.helloworld.retrofit.ApiService
import com.example.helloworld.retrofit.RetrofiBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import okhttp3.*
import retrofit2.Retrofit
import java.io.IOException
import java.io.StringReader
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val URL = "https://api.kinopoisk.dev/movie"
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private var items: List<NewItem> = mutableListOf()
    private var db: FilmDb? = null
    private lateinit var txt: TextView
    private lateinit var prgBar: ProgressBar
    private lateinit var rndPrg: ProgressBar
    private var j =0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button2)
        val btnAppDb = findViewById<Button>(R.id.button_app_db)
        val btnDrop = findViewById<Button>(R.id.button_destroy_db)
        val btnRetro = findViewById<Button>(R.id.button_retrofit)
        val btnCor = findViewById<Button>(R.id.button_coroutines)
        txt = findViewById<TextView>(R.id.textView)
        prgBar = findViewById(R.id.progress)
        rndPrg = findViewById(R.id.round_progress)
        prgBar.min = 0
        prgBar.max = 200

        items = fillList()
        //btn.setOnClickListener{ setImage() }
        btn.setOnClickListener{ Clickfun_1() }
        btn2.setOnClickListener{ request() }
        btnAppDb.setOnClickListener{ appDb() }
        btnDrop.setOnClickListener{ dropDb() }
        btnRetro.setOnClickListener{ retrofit() }
        btnCor.setOnClickListener{ coroutines() }

    }

    private fun coroutines(){
        j = 0
        rndPrg.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Default){
            startCoroutines{
                    i-> withContext(Dispatchers.Main){
                updateResults(i)
                }
            }
        }
    }

    private fun updateResults(i: Int){

        j += i
        txt.text = j.toString()
        prgBar.progress = j
        if(j==200) rndPrg.visibility = View.INVISIBLE
        Log.d("coroutines", j.toString())
    }

    private suspend fun startCoroutines(updateResults: suspend (i: Int) -> Unit
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
            updateResults(1)

        }
    }

    private suspend fun coroutineOne(channel: Channel<Int>){
        for(i in 1..100){
            delay(800L)
            channel.send(i)
        }
    }

    private suspend fun coroutineTwo(channel: Channel<Int>){
        for(i in 101..200){
            delay(1000L)
            channel.send(1)
        }
    }

    private fun retrofit() {
        GlobalScope.launch {
        val serv: ApiService = RetrofiBuilder.apiService
            val rep = serv
            .getFilms()
            .execute()
            .body() ?: listOf()
        Log.d("------------------", rep.toString())
        }
    }

    private fun dropDb(){
        this.applicationContext.deleteDatabase("movies.db")
    }

    private fun appDb(){
        Executors.newSingleThreadScheduledExecutor().execute(
            Runnable {
                db = DbObject.getDatabase(this.applicationContext)
  /*              Log.d("------appDb", db.toString())
                val film = Film("name3", 1,"short description3", "preview url3")
                val film2 = Film("name4", 1,"short description4", "preview url4")
                db?.filmDao()?.insert(film)
                db?.filmDao()?.insert(film2)*/
            }
        )

    }


    val str = """ 
    {
    "docs":
        [
            {
                "externalId":
                {
                    "_id":"62188d02651ebcb9df14cb78",
                    "imdb":"tt4452272"
                },
                "poster":
                {
                    "_id":"62188d02651ebcb9df14cb7a",
                    "url":"https://st.kp.yandex.net/images/film_big/901914.jpg",
                    "previewUrl":"https://st.kp.yandex.net/images/film_iphone/iphone360_901914.jpg"
                },
                "rating":
                {
                    "_id":"62188d02651ebcb9df14cb7b",
                    "kp":0,
                    "imdb":0,
                    "filmCritics":0,
                    "russianFilmCritics":0,
                    "await":0
                },
                "votes":
                {
                    "_id":"62188d02651ebcb9df14cb7c",
                    "kp":2,
                    "imdb":0,
                    "filmCritics":0,
                    "russianFilmCritics":0,
                    "await":0
                },
                "id":901914,
                "type":"movie",
                "name":null,
                "description":null,
                "year":1917,
                "alternativeName":"Military Madness",
                "enName":null,
                "movieLength":null,
                "names":
                [
                    {
                        "_id":"62188d02651ebcb9df14cb79",
                        "name":"Military Madness"
                    }
                ],
                "shortDescription":null
            }
        ],
        "total":880081,
        "limit":1,
        "page":1,
        "pages":880081
    }    
    """








    private fun request()
    {
        var page: Int = 1
        var pagesCount: Int = 1

        val urlBuilder : HttpUrl.Builder = HttpUrl.parse(URL)?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token","MKRJKN4-Q0B463J-J85RBPK-ENWYABY")
//       urlBuilder.addQueryParameter("search","435")
//        urlBuilder.addQueryParameter("field","id")

/*        urlBuilder.addQueryParameter("search","1999")
        urlBuilder.addQueryParameter("field","year")
        urlBuilder.addQueryParameter("search","Зеленая миля")
        urlBuilder.addQueryParameter("field","name")*/
        urlBuilder.addQueryParameter("page","2")
//        urlBuilder.addQueryParameter("limit","2")
        val url : String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("response", "вот чёт то не удалось")
            }

            private val itemMap: MutableMap<String, Any> = mutableMapOf(
                "id" to 0,
                "name" to "",
                "shortDescription" to "",
                "alternativeName" to "",
                "poster" to "",
                "previewUrl" to ""
            )

            private fun readObject(reader: JsonReader){
                var nm: String
                reader.beginObject()
                while (reader.hasNext()) {
                    if(reader.peek()!=JsonToken.NAME) reader.skipValue()
                    nm = reader.nextName()
                    Log.d("response", nm)
                    if(reader.peek() == JsonToken.NULL) reader.skipValue()
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


            override fun onResponse(call: Call, response: Response) {
                Log.d("response", "---------------------------------")

                val json = response.body()?.string()
                val reader = JsonReader(StringReader(json))
                var nm: String

                Log.d("response", "вот он респонс сырой: $json")
                reader.beginObject()
                while (reader.hasNext()) {
                    nm = reader.nextName()
                    if (nm == "docs") {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            readObject(reader)
                            Log.d("film_add", itemMap.toString())
                            db?.filmDao()?.insert(Film(itemMap))
                        }
                        reader.endArray()
                    }
                    else{
                        Log.d("response", "$nm : ${reader.nextInt()}")
                    }
                }
                reader.endObject()
            }
        })
    }


    private fun fillList():List<NewItem>{
        val list = mutableListOf<NewItem>()
        val map = mutableMapOf<String, String>()
        val reader = JsonReader(StringReader(resources.getString(R.string.movies)))
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) map[reader.nextName()] = reader.nextString()
            reader.endObject()
            list.add(
                NewItem(
                    map["name"] as String,
                    map["description"] as String,
                    map["pictures"] as String
                )
            )
        }
        reader.endArray()
        return list
    }



    private fun Clickfun_1()
    {
        val intent = Intent(this@MainActivity, SecondActivity::class.java)
        startActivity(intent)
        //items.forEach{println(it)}
    //getRes(this)
    }


    private fun setImage(){
        Log.d("tag", "setImage")
        val img = findViewById<ImageView>(R.id.imageView)
        val txt = findViewById<TextView>(R.id.textView)
        val drawableName : String = "potter"
        var resID : Int = resources.getIdentifier(drawableName, "drawable", packageName)
        Log.d("tag", packageName)
        Log.d("tag", resID.toString())
        img.setImageResource(resID)

        val jsonStr: String = resources.getString(R.string.movies)
        Log.d("jsonStr", jsonStr)
        val stream: StringReader = StringReader(jsonStr)
        Log.d("read json", "StringReader")
        val reader: JsonReader = JsonReader(stream)
        Log.d("read json", "JsonReader: ${reader.toString()}")
        //reader.isLenient = true
        reader.beginArray()
        Log.d("read json", "beginArray()")
        var name: String = ""
        var value: String = ""

        while (reader.hasNext())
        {
            Log.d("read json", "next object")
            reader.beginObject()
            Log.d("read json", "beginObject()")
            while (reader.hasNext())
            {
                Log.d("read json", "want name")
                name = reader.nextName()
                value = reader.nextString()
                println("$name : $value")
            }
            print("\n")
            reader.endObject()
        //Log.d("Object",reader.nextString())
        }
        reader.endArray()
        println("\n$name : $value")
        resID = resources.getIdentifier(value, "drawable", packageName)
        img.setImageResource(resID)
        txt.text = value
    }
}