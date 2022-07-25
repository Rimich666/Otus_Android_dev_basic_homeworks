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
}

class Sorting{
    @Path(".sorting[.sortField")
    var sortField: String = ""
    @Path(".sorting[.sortType")
    var sortType: String = ""
}

class Parameters{
    @Path(".searching[")
    var searchList: MutableList<Searching> = mutableListOf()

    @Path(".sorting[")
    var sortList: MutableList<Sorting> = mutableListOf()

    @Path("dd")
    lateinit var sort: Sorting
}

object RemoteConfig {
    private const val HTTP_REQUEST_SEARCH_PARAMETERS = "http_request_search_parameters"
    private const val HTTP_REQUEST_SORT_PARAMETERS = "http_request_search_parameters"

    private const val HTTP_REQUEST_PARAMETERS = "parameters"
    private val DEFAULTS = hashMapOf<String, Any>(
        HTTP_REQUEST_SEARCH_PARAMETERS to mutableMapOf<String, String>(),
        HTTP_REQUEST_SORT_PARAMETERS to mutableMapOf<String, String>(),
        HTTP_REQUEST_PARAMETERS to "{}"
    )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
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
            Log.d(RCTAG, remoteConfig.getString(HTTP_REQUEST_PARAMETERS))
            val parameters = parseJson(remoteConfig.getString(HTTP_REQUEST_PARAMETERS), Parameters())
            Log.d(RCTAG, remoteConfig.getString(HTTP_REQUEST_SEARCH_PARAMETERS))
            Log.d(RCTAG, "addOnCompleteListener")
        }

        return remoteConfig
    }

    fun getQueryParameters(): MutableMap<String, String> =
        remoteConfig.getString(HTTP_REQUEST_SEARCH_PARAMETERS) as MutableMap<String, String>

}