package com.moviesearch.datasource.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.moviesearch.ui.NewItem
import java.time.LocalDateTime

@Entity(
    tableName = "list_films",
    indices = [Index("id_kp", unique = true)],
    foreignKeys = [ForeignKey(entity = Page::class, parentColumns = ["id"], childColumns = ["page_id"], onDelete = CASCADE)]
)
data class Film(
    val name: String,
    @ColumnInfo(name = "id_kp") val idKp: Int,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "alternativeName") val alternativeName: String,
    @ColumnInfo(name = "page_id") val page: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    constructor(map: Map<String, *>, page_id: Long):this(
        map["name"] as String,
        map["id"] as Int,
        map["shortDescription"] as String,
        map["previewUrl"] as String,
        map["alternativeName"] as String,
        page_id
    )
}

@Entity(
    tableName = "favourites",
    indices = [Index("id_kp", unique = true)],
)
data class Favourite(
    val name: String,
    @ColumnInfo(name = "id_kp") val idKp: Int,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "alternativeName") val alternativeName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    constructor(item: NewItem): this(
        item.name,
        item.idKp,
        item.description,
        item.pictures,
        item.altName
    )
}

@Entity(tableName = "pages", indices = [Index("num", unique = true)])
data class Page(val num: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Entity(
    tableName = "deferred_films",
    indices = [Index("id_kp", unique = true), Index("id_work", unique = true)]
)
data class DeferredFilm(
    val name: String,
    @ColumnInfo(name = "date_time") val dateTime: String,
    @ColumnInfo(name = "id_work") val idWork: String,
    @ColumnInfo(name = "id_kp") val idKp: Int,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "alternativeName") val alternativeName: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    constructor(item: NewItem): this(
        item.name,
        item.deferDateTime,
        item.idWork,
        item.idKp,
        item.description,
        item.pictures,
        item.altName
    )
}