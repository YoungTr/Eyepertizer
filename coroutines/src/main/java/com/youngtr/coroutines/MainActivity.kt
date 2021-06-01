package com.youngtr.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youngtr.coroutines.data.api.ApiHelperImpl
import com.youngtr.coroutines.data.api.RetrofitBuilder
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)
    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create the job
        job = Job()
        setContentView(R.layout.activity_main)

        println("onCreate start")

//        forAsync()
        forLaunch()
        println("onCreate end")

        launch { }
    }

    private fun forLaunch() {
        GlobalScope.launch(Dispatchers.Main) {
            println("fetch start --- ${currentThreadName()}")
            val users = apiHelper.getUsers()
            println("users size: ${users.size} --- ${currentThreadName()}")
            val moreUsers = apiHelper.getMoreUsers()
            println("moreUsers size: ${moreUsers.size}")
            println("fetchEnd --- ${currentThreadName()}")

        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private fun forAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            println("fetch start --- ${currentThreadName()}")
            val user = async {
                println("fetch users start --- ${currentThreadName()}")
                apiHelper.getUsers()
            }
            val moreUser = async {
                println("delay")
                delay(200)
                println("fetch more users start --- ${currentThreadName()}")
                apiHelper.getMoreUsers()
            }

            val users = user.await()
            println("users size: ${users.size} --- ${currentThreadName()}")
            val moreUsers = moreUser.await()
            println("moreUsers size: ${moreUsers.size}")
            println("fetchEnd --- ${currentThreadName()}")
        }
    }


    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}