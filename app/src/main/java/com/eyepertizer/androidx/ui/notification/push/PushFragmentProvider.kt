package com.eyepertizer.androidx.ui.notification.push

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import dagger.Module
import dagger.Provides

@Module
class PushFragmentProvider {

    @Provides
    fun providePushViewModel(
        fragment: PushFragment,
        dataManager: IDataManager
    ): PushViewModel {
        val supplier: Supplier<PushViewModel> =
            Supplier { PushViewModel(dataManager) }
        val factory: ViewModelProviderFactory<PushViewModel> =
            ViewModelProviderFactory(PushViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(PushViewModel::class.java)
    }
}