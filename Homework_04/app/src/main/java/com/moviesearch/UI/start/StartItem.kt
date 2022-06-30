package com.moviesearch.UI.start

import kotlin.properties.Delegates

class RequestedItem(var action: String, var successful: Boolean?)

class InitCashItem(var aText: String){
    var max: Int = 0
    var progress: Int = 0
}

sealed class StartItem{
    class Requested(val requested: RequestedItem) : StartItem()
    class InitCash(val initCash: InitCashItem): StartItem()
}