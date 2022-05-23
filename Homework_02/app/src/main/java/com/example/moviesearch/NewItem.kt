package com.example.moviesearch

import android.os.Bundle
import androidx.core.os.bundleOf

data class NewItem(val name: String, val description: String, val pictures: String, var Selected: Boolean)
{
    val bundle: Bundle
        get() = bundleOf(
            "name" to name,
            "description" to description,
            "pictures" to pictures,
            "Selected" to Selected
        )
}