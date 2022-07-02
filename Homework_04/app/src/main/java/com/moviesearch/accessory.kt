package com.moviesearch

import android.util.Log

fun trace(): String{
    return "${Throwable().stackTrace[1].fileName}: ${Throwable().stackTrace[1].lineNumber}: "
}

fun trace(all: Boolean): String{
    return "${Throwable().stackTrace.size}:"
}

object Keys{
    const val codeResponse = "codeResponse"
    const val requestedPage = "requestedPage"
    const val successful = "successful"
    const val requested = "requested"
    const val complete = "complete"

    const val pages = "pages"
    const val favour = "favour"
    const val max = "max"

    const val progress = "progress"


}
