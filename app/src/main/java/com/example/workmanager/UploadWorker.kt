package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class UploadWorker(context: Context,params:WorkerParameters):Worker(context,params){
    companion object{
        const val KEY_NAME="KEY_NAME"
    }
    override fun doWork(): Result {

        try {
            val count=inputData.getInt(MainActivity.KEY_VALUE_COUNT,0)

            val data=Data.Builder().putString(KEY_NAME,"Haider") //sending data from workmanager
                .build()
            for (i in 0 until count){
                Log.d("uploading", "doWork: $i")

            }
            return Result.success(data)
        }catch (e:Exception){
            Log.d("checking", "doWork: $e")

            return Result.failure()
        }
    }
}