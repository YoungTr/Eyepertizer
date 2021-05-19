package com.eyepertizer.androidx.data

import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.pref.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper
) : IDataManager {


    override fun getFirstEntryApp(): Boolean {
        return preferenceHelper.getFirstEntryApp()
    }

    override fun setFirstEntryApp(isFirst: Boolean) {
        preferenceHelper.setFirstEntryApp(isFirst)
    }
}