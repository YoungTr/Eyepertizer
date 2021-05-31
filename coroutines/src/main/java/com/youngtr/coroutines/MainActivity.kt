package com.youngtr.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create the job
        setContentView(R.layout.activity_main)

        launch {

        }

        val handler = CoroutineExceptionHandler() { _, exception ->

        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job


    override fun onDestroy() {
        job.cancel()    // cancel the job
        super.onDestroy()
    }
}