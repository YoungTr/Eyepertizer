package com.eyepertizer.androidx.di.module

import android.app.Application
import android.content.Context
import com.eyepertizer.androidx.data.AppDataManager
import com.eyepertizer.androidx.data.IDataManager
import com.eyepertizer.androidx.data.db.AppDbHelper
import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.network.AppApiHelper
import com.eyepertizer.androidx.data.pref.AppPreferenceHelper
import com.eyepertizer.androidx.data.pref.PreferenceHelper
import com.eyepertizer.androidx.di.PreferenceInfo
import com.eyepertizer.androidx.util.AppConstants
import dagger.Module
import dagger.Provides
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

    @PreferenceInfo
    @Provides
    fun providePreferenceFileName(): String = AppConstants.PREF_NAME
}