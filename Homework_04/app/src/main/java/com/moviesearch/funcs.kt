package com.moviesearch

import android.util.Log

fun trace(): String{
    return "${Throwable().stackTrace[1].fileName}: ${Throwable().stackTrace[1].lineNumber}: "
}

fun trace(all: Boolean): String{
    return "${Throwable().stackTrace.size}:"
}