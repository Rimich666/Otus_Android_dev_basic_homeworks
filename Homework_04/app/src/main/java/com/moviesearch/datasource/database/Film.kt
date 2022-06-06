package com.moviesearch.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_films")
data class Film(
    val name: String,
    @ColumnInfo(name = "id_kp") val idKp: Int,
    @ColumnInfo(name = "short_description") val shortDescription: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "preview_local") val previewLocal: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}