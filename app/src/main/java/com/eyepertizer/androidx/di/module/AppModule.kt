package com.eyepertizer.androidx.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.db.AppDbHelper
import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.db.dao.AppDatabase
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchRepo
import com.eyepertizer.androidx.data.db.dao.repository.search.SearchRepository
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.network.AppApiHelper
import com.eyepertizer.androidx.data.pref.AppPreferenceHelper
import com.eyepertizer.androidx.data.pref.PreferenceHelper
import com.eyepertizer.androidx.di.PreferenceInfo
import com.eyepertizer.androidx.util.AppConstants
import com.eyepertizer.androidx.util.AppConstants.APP_DB_NAME
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context = application

    @Singleton
    @Provides
    fun providePreferenceHelper(preferenceHelper: AppPreferenceHelper): PreferenceHelper {
        return preferenceHelper
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: AppApiHelper): ApiHelper {
        return apiHelper
    }

    @Singleton
    @Provides
    fun provideDbHelper(dbHelper: AppDbHelper): DbHelper {
        return dbHelper
    }

    @Singleton
    @Provides
    fun provideDataManager(dataManager: AppDataManager): IDataManager {
        return dataManager
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, APP_DB_NAME).build()

    @Singleton
    @Provides
    fun provideSearchRepository(appDatabase: AppDatabase): SearchRepo =
        SearchRepository(appDatabase.searchDao())

    @PreferenceInfo
    @Provides
    fun providePreferenceFileName(): String = AppConstants.PREF_NAME


    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}