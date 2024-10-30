package com.example.workmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl


class MainActivity : AppCompatActivity() {
    private lateinit var workManagerButton: Button
    private lateinit var textViewResult: TextView
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
        val uploadWorker = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .build()
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorker.id)
            .observe(this, Observer {
                textViewResult.text = it.state.name
            })
        manager.enqueue(uploadWorker)
    }
}