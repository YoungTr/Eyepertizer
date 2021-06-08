package com.eyepertizer.androidx.ui.community.commend

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import dagger.Module
import dagger.Provides

@Module
class CommendFragmentProvider {

    @Provides
    fun provideCommendViewModel(
        fragment: CommendFragment,
        dataManager: IDataManager
    ): CommendViewModel {
        val supplier: Supplier<CommendViewModel> =
            Supplier { CommendViewModel(dataManager) }
        val factory: ViewModelProviderFactory<CommendViewModel> =
            ViewModelProviderFactory(CommendViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(CommendViewModel::class.java)
    }
}