package com.example.helloworld.retrofit

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val kpId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortDescription")
    val shortDescription: String
)
