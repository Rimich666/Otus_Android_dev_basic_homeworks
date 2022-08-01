package com.moviesearch.firebase

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.BuildConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.moviesearch.MyObject
import com.moviesearch.Path
import com.moviesearch.RCTAG
import com.moviesearch.parseJson

@MyObject
class Searching{

    @Path(".searching[.field")
    var field: String = ""

    @Path(".searching[.search")
    var search: String = ""

    override fun toString(): String {
        return "field: $field, search: $search"
    }
}

@MyObject
class Sorting{

    @Path(".sorting[.sortField")
    var sortField: String = ""

    @Path(".sorting[.sortType")
    var sortType: String = ""

    override fun toString(): String {
        return "sortField: $sortField, sortType: $sortType"
    }
}

class Parameters{

    @Path(".searching[")
    var searchList: MutableList<Searching> = mutableListOf()

    @Path(".sorting[")
    var sortList: MutableList<Sorting> = mutableListOf()

    fun searchingMmap(): MutableMap<String, String>{
        val map = mutableMapOf<String, String>()
        searchList.forEach { map[it.field] = it.search }
        return map
    }

    fun sortingMap(): MutableMap<String, String>{
        val map = mutableMapOf<String, String>()
        sortList.forEach { map[it.sortField] = it.sortType }
        return map
    }

    override fun toString(): String{
        var retStr = "\nSearch:\n"
        searchList.forEach{ retStr += "\t$it\n" }
        retStr += "Sort:\n"
        sortList.forEach { retStr += "\t$it\n" }
        return retStr
    }
}

object RemoteConfig {
    private const val HTTP_REQUEST_SEARCH_PARAMETERS = "http_request_search_parameters"
    private const val HTTP_REQUEST_SORT_PARAMETERS = "http_request_sort_parameters"

    private const val HTTP_REQUEST_PARAMETERS = "parameters"

    private var parameters = Parameters()

    private val DEFAULTS = hashMapOf<String, Any>(
        HTTP_REQUEST_SEARCH_PARAMETERS to parameters.searchList,
        HTTP_REQUEST_SORT_PARAMETERS to parameters.sortList,
        HTTP_REQUEST_PARAMETERS to {}
    )


    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
        parameters = parseJson(remoteConfig.getString(HTTP_REQUEST_PARAMETERS), Parameters()) as Parameters
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {

        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            if (BuildConfig.DEBUG) {
                minimumFetchIntervalInSeconds = 0 // Kept 0 for quick debug
            } else {
                minimumFetchIntervalInSeconds = 0
            //minimumFetchIntervalInSeconds = 60 * 60 // Change this based on your requirement
            }
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            Log.d(RCTAG,RemoteConfig.remoteConfig.getString(HTTP_REQUEST_PARAMETERS))
            Log.d(RCTAG, "addOnCompleteListener")
        }

        return remoteConfig
    }

    fun getSearchMap(): Map<String, String> = parameters.searchingMmap()

    fun getSortMap(): Map<String, String> = parameters.sortingMap()
}