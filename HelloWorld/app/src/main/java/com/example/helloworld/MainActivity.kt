package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.helloworld.database.DbObject
import com.example.helloworld.database.Film
import com.example.helloworld.database.FilmDb
import com.example.helloworld.retrofit.ApiService
import com.example.helloworld.retrofit.RetrofiBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        val btn2 = findViewById<Button>(R.id.button2)
        val btnAppDb = findViewById<Button>(R.id.button_app_db)
        val btnDrop = findViewById<Button>(R.id.button_destroy_db)
        val btnRetro = findViewById<Button>(R.id.button_retrofit)
        items = fillList()
        //btn.setOnClickListener{ setImage() }
        btn.setOnClickListener{ Clickfun_1() }
        btn2.setOnClickListener{ request() }
        btnAppDb.setOnClickListener{ appDb() }
        btnDrop.setOnClickListener{ dropDb() }
        btnRetro.setOnClickListener{ retrofit()}
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
                Log.d("------appDb", db.toString())
                val film = Film("name3", 1,"short description3", "preview url3")
                val film2 = Film("name4", 1,"short description4", "preview url4")
                db?.filmDao()?.insert(film)
                db?.filmDao()?.insert(film2)
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

        val urlBuilder : HttpUrl.Builder = HttpUrl.parse(URL)?.newBuilder() ?: error("URL не удался")
        urlBuilder.addQueryParameter("token","MKRJKN4-Q0B463J-J85RBPK-ENWYABY")
//       urlBuilder.addQueryParameter("search","435")
//        urlBuilder.addQueryParameter("field","id")

        urlBuilder.addQueryParameter("search","1999")
        urlBuilder.addQueryParameter("field","year")
        urlBuilder.addQueryParameter("search","Зеленая миля")
        urlBuilder.addQueryParameter("field","name")

        urlBuilder.addQueryParameter("limit","2")
        val url : String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("response", "вот чёт то не удалось")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("response", "---------------------------------")

                val json = response.body()?.string()
                val reader = JsonReader(StringReader(json))
                var nm: String
                var jt: JsonToken
                Log.d("response", "вот он респонс сырой: $json")
                reader.beginObject()
                while (reader.hasNext()) {
                    nm = reader.nextName()
                    if (nm == "docs") {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            reader.beginObject()
                            while (reader.hasNext()) {
                                nm = reader.nextName()
                                Log.d("response", nm)

                                when (nm) {
                                    "id" -> Log.d("response", "id = ${reader.nextInt()}")
                                    "shortDescription" -> Log.d(
                                        "response",
                                        "shortDescription = ${reader.nextString()}"
                                    )
                                    else -> {
                                        Log.d("response", "ну да где то здесь")
                                        reader.skipValue()
                                    }
                                }
                            }
                            reader.endObject()
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