package com.eyepertizer.mvvm.ui.main.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.mvvm.data.api.ApiHelper
import com.eyepertizer.mvvm.data.repository.MainRepository
import com.eyepertizer.mvvm.ui.main.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}