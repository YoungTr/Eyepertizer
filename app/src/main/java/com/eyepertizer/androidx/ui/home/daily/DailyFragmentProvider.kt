package com.eyepertizer.androidx.ui.home.daily

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import dagger.Module
import dagger.Provides

@Module
class DailyFragmentProvider {

    @Provides
    fun provideDailyViewModel(
        fragment: DailyFragment,
        dataManager: IDataManager
    ): DailyViewModel {
        val supplier: Supplier<DailyViewModel> =
            Supplier { DailyViewModel(dataManager) }
        val factory: ViewModelProviderFactory<DailyViewModel> =
            ViewModelProviderFactory(DailyViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(DailyViewModel::class.java)
    }
}