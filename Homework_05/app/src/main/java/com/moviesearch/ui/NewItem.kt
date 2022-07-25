package com.moviesearch.ui

import androidx.room.ColumnInfo
import androidx.work.workDataOf
import com.moviesearch.Path
import com.moviesearch.datasource.database.DeferredFilm
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.database.Film
import java.util.*

data class NewItem(
    val idKp: Int,
    val name: String,
    val altName: String,
    val description: String,
    val pictures: String,
    var poster: String ,
)
{
    var selected: Boolean = false
    var liked: Boolean = false
    var deferred: Boolean = false
    var deferDateTime: String = ""
    var idWork: String = ""

    constructor(itmap:Map<String,String>, selected: Boolean, liked: Boolean):this(
            0,
            itmap["name"] as String,
            "altName",
            itmap["description"] as String,
            itmap["pictures"] as String,
            itmap["poster"] as String,
        ){
        this.selected = selected
        this.liked = liked
    }

    constructor(itemMap: MutableMap<*, *>):this(
            itemMap["id"] as Int,
            itemMap["name"] as String,
            itemMap["alternativeName"] as String,
            itemMap["shortDescription"] as String,
            itemMap["previewUrl"] as String,
            itemMap["poster"] as String,
        ){
        this.liked = itemMap["licked"] as Boolean
        this.deferred = itemMap["deferred"] as Boolean
    }

    constructor(rec: Favourite): this(
            rec.idKp,
            rec.name,
            rec.alternativeName,
            rec.shortDescription,
            rec.previewUrl,
            "",
        ){
        this.liked = true
    }

    constructor(rec: DeferredFilm): this(
        rec.idKp,
        rec.name,
        rec.alternativeName,
        rec.shortDescription,
        rec.previewUrl,
        ""){
        this.deferDateTime = rec.dateTime
        this.idWork = rec.idWork
    }

    constructor(rec: Film, liked: Boolean): this(
            rec.idKp,
            rec.name,
            rec.alternativeName,
            rec.shortDescription,
            rec.previewUrl,
            "",
        ){
        this.liked = liked
    }

    fun workData() = workDataOf(
        "idKp" to idKp,
        "name" to name,
        "altName" to altName,
        "description" to description,
        "pictures" to pictures
    )

    fun copy(): NewItem {
        val item = NewItem(idKp, name, altName, description, pictures, poster)
        item.selected = selected
        item.liked = liked
        item.deferred = deferred
        item.deferDateTime = deferDateTime
        item.idWork = idWork
        return item
    }
}

class Details(){
    @Path(".poster.url")
    var poster: String = ""
    @Path(".name")
    var name: String = ""
    @Path(".type")
    var type: String = ""
    @Path(".year")
    var year: Int = 0

    override fun toString(): String{
        return "\nname = $name\ntype = $type\nyear = $year\nposter = $poster"
    }
}