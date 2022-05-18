package com.example.helloworld

import android.content.res.Resources
import android.util.Log
import java.security.AccessController.getContext

fun getRes()
{
    val cont = getContext()
    val asset = getAssets()
    Log.d("Tag", "get resources function")
    Log.d("getRes", cont.toString())
}