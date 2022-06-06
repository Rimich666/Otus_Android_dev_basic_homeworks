package com.moviesearch.UI

import android.os.Bundle
import androidx.core.os.bundleOf

data class NewItem(val name: String, val description: String, val pictures: String, var poster: String ,var Selected: Boolean, var liked: Boolean)
{
    constructor(itmap:Map<String,String>, Selected: Boolean, liked: Boolean):this(
        itmap["name"] as String,
        itmap["description"] as String,
        itmap["pictures"] as String,
        itmap["poster"] as String,
        Selected,
        liked)

    constructor(bundle: Bundle):this(
        bundle["name"] as String,
        bundle["description"] as String,
        bundle["pictures"] as String,
        bundle["poster"] as String,
        bundle["selected"] as Boolean,
        bundle["liked"] as Boolean)

    val bundle: Bundle
        get() {
            return bundleOf(
                "name" to name,
                "description" to description,
                "pictures" to pictures,
                "poster" to poster,
                "selected" to Selected,
                "liked" to liked
            )
        }

}
