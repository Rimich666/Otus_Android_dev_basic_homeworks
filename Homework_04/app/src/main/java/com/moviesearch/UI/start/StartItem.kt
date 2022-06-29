package com.moviesearch.UI.start

class RequestedItem(var action: String, result: String)

class InitCashItem(var aText: String)

sealed class StartItem{
    class Requested(val requested: Requested) : StartItem()
    class InitCash(val initCash: InitCashItem): StartItem()
}