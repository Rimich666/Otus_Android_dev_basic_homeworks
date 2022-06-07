package com.example.helloworld.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_films")
data class Film (
    val name: String,
    @ColumnInfo(name = "id_kp") val idKp: Int,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name="alternative_name") val alternativeName: String
    ){
    constructor(map: Map<String, *>):this(
        map["name"] as String,
        map["id"] as Int,
        map["shortDescription"] as String,
        map["previewUrl"] as String,
        map["alternativeName"] as String
    )
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    //var preview: Any? = null
}