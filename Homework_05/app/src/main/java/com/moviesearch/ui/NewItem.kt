package com.moviesearch.ui

import androidx.work.workDataOf
import com.moviesearch.datasource.database.Favourite
import com.moviesearch.datasource.database.Film

data class NewItem(
    val idKp: Int,
    val name: String,
    val altName: String,
    val description: String,
    val pictures: String,
    var poster: String ,
    var Selected: Boolean,
    var liked: Boolean,
    var deferred: Boolean,
    var deferDateTime: String
)
{
    constructor(itmap:Map<String,String>, Selected: Boolean, liked: Boolean):this(
        0,
        itmap["name"] as String,
        "altName",
        itmap["description"] as String,
        itmap["pictures"] as String,
        itmap["poster"] as String,
        Selected,
        liked,
        false,
        ""
    )

    constructor(itemMap: MutableMap<*, *>):this(
        itemMap["id"] as Int,
        itemMap["name"] as String,
        itemMap["alternativeName"] as String,
        itemMap["shortDescription"] as String,
        itemMap["previewUrl"] as String,
        itemMap["poster"] as String,
        false,
        itemMap["licked"] as Boolean,
        false,
        ""
    )

    constructor(rec: Favourite): this(
        rec.idKp,
        rec.name,
        rec.alternativeName,
        rec.shortDescription,
        rec.previewUrl,
        "",
        false,
        true,
        false,
        ""
    )
    constructor(rec: Film, liked: Boolean): this(
        rec.idKp,
        rec.name,
        rec.alternativeName,
        rec.shortDescription,
        rec.previewUrl,
        "",
        false,
        liked,
        false,
        ""
    )
    fun workData() = workDataOf(
        "name" to name,
        "altName" to altName,
        "description" to description,
        "pictures" to pictures,
        "dateTime" to deferDateTime
    )

    fun copy(): NewItem = NewItem(
        idKp, name, altName, description, pictures, poster, Selected, liked, deferred, deferDateTime)
}
