package com.moviesearch.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moviesearch.WMTAG
import com.moviesearch.trace

class DetailWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
    override fun doWork(): Result {
        Log.d(WMTAG, "${trace()} ${this.id}")
        Log.d(WMTAG, "${trace()} ${inputData.keyValueMap.keys}")
        Log.d(WMTAG, "${trace()} ${inputData.keyValueMap.values}")
        val data = inputData.keyValueMap
        Notification.createNotification(data["name"].toString(), data["altName"].toString(),
            data["description"].toString(), true, data["idKp"].toString().toInt())
        return Result.success()
    }
}
