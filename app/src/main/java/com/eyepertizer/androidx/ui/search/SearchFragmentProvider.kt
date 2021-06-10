package com.eyepertizer.androidx.ui.search

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.eyepertizer.androidx.base.viewmodel.ViewModelProviderFactory
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchRepo
import dagger.Module
import dagger.Provides

@Module
class SearchFragmentProvider {

    @Provides
    fun provideSearchViewModel(
        fragment: SearchFragment,
        dataManager: IDataManager,
        searchRepo: SearchRepo
    ): SearchHotViewModel {
        val supplier: Supplier<SearchHotViewModel> =
            Supplier { SearchHotViewModel(dataManager, searchRepo) }
        val factory: ViewModelProviderFactory<SearchHotViewModel> =
            ViewModelProviderFactory(SearchHotViewModel::class.java, supplier)
        return ViewModelProvider(fragment, factory).get(SearchHotViewModel::class.java)
    }
}