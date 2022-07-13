package com.moviesearch.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.moviesearch.trace

class DetailWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
    override fun doWork(): Result {
        Log.d("workManager", "${trace()} Это Work")
        return Result.success()
    }
}