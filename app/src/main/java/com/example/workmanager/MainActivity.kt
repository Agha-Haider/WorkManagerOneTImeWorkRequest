package com.example.workmanager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl
import androidx.work.setInputMerger


class MainActivity : AppCompatActivity() {
    private lateinit var workManagerButton: Button
    private lateinit var textViewResult: TextView

    companion object{
        const val KEY_VALUE_COUNT="KEYVALUECOUNT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workManagerButton = findViewById(R.id.button_work)
        textViewResult = findViewById(R.id.textView_result)

        workManagerButton.setOnClickListener {
            callWorkManager()
        }

    }

    private fun callWorkManager() {


        val manager = WorkManager.getInstance(this)

        val constraints=Constraints.Builder()   //setting constraints for workmanager
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val data:Data=Data.Builder().putInt(KEY_VALUE_COUNT,125)   ////sending data to workmanager
            .build()

        val uploadWorker = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorker.id)
            .observe(this, Observer {
                textViewResult.text = it.state.name

                if (it.state.isFinished){
                    val data=it.outputData
                    val message=data.getString(UploadWorker.KEY_NAME) //getting data from workmanager
                    Log.d("messagefromworkmanager", "callWorkManager: "+message)
                }
            })
        manager.enqueue(uploadWorker)
    }
}