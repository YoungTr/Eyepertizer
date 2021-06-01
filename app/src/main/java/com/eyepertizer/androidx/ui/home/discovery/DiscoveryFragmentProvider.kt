package com.eyepertizer.androidx.ui.home.discovery

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class DiscoveryFragmentProvider {

    @Provides
    fun provideDiscoveryViewModel(
        fragment: DiscoveryFragment,
        dataManager: IDataManager,
        compositeDisposable: CompositeDisposable
    ): DiscoveryViewModel {
        val supplier: Supplier<DiscoveryViewModel> =
            Supplier { DiscoveryViewModel(dataManager, compositeDisposable) }
        val factory: ViewModelProviderFactory<DiscoveryViewModel> =
            ViewModelProviderFactory(DiscoveryViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(DiscoveryViewModel::class.java)
    }
}