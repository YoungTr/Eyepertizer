package com.eyepertizer.androidx.data

import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.network.model.HomePageRecommend
import com.eyepertizer.androidx.data.pref.PreferenceHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper,
) : IDataManager {


    override fun getFirstEntryApp(): Boolean {
        return preferenceHelper.getFirstEntryApp()
    }

    override fun setFirstEntryApp(isFirst: Boolean) {
        preferenceHelper.setFirstEntryApp(isFirst)
    }

    override fun setUUID(uuid: String) {
        preferenceHelper.setUUID(uuid)
    }

    override fun getUUID(): String? {
        return preferenceHelper.getUUID()
    }

    override fun getHomePageRecommend(url: String): Observable<HomePageRecommend> {
        return apiHelper.getHomePageRecommend(url)
    }
}