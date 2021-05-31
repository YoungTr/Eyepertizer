package com.youngtr.coroutines.data.api

import com.youngtr.coroutines.data.model.ApiUser


interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}