package com.eyepertizer.androidx.ui.community.follow

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import dagger.Module
import dagger.Provides

@Module
class FollowFragmentProvider {

    @Provides
    fun provideFollowViewModel(
        fragment: FollowFragment,
        dataManager: IDataManager
    ): FollowViewModel {
        val supplier: Supplier<FollowViewModel> =
            Supplier { FollowViewModel(dataManager) }
        val factory: ViewModelProviderFactory<FollowViewModel> =
            ViewModelProviderFactory(FollowViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(FollowViewModel::class.java)
    }
}