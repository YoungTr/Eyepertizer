package com.eyepertizer.mvvm.data.api

import com.eyepertizer.mvvm.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers(): Single<List<User>>
}