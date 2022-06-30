package com.moviesearch.UI.start

class RequestedItem(var action: String, var result: String)

class InitCashItem(var aText: String){
    var max: Int = 0
    var progress: Int = 0
}

sealed class StartItem{
    class Requested(val requested: RequestedItem) : StartItem()
    class InitCash(val initCash: InitCashItem): StartItem()
}