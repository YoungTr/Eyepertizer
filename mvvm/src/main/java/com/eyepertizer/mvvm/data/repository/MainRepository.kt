package com.eyepertizer.mvvm.data.repository

import com.eyepertizer.mvvm.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers() = apiHelper.getUsers()
}