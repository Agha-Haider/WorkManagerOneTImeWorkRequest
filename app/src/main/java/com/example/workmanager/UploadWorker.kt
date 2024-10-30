package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class UploadWorker(context: Context,params:WorkerParameters):Worker(context,params){
    override fun doWork(): Result {

        try {
            for (i in 1..600){
                Log.d("checking", "doWork: $i")
            }
            return Result.success()
        }catch (e:Exception){
            Log.d("checking", "doWork: $e")

            return Result.failure()
        }
    }
}