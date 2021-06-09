package com.eyepertizer.androidx.base.viewmodel

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory<T : ViewModel> constructor(
    private val viewModelClass: Class<T>,
    private val viewModelSupplier: Supplier<T>
) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            return viewModelSupplier.get() as T
        } else {
            throw IllegalArgumentException("Unknown Class name $viewModelClass ")
        }
    }
}