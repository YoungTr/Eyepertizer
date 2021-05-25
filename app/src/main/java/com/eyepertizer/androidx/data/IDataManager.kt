package com.eyepertizer.androidx.data

import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.network.model.VideoDetail
import com.eyepertizer.androidx.data.pref.PreferenceHelper
import io.reactivex.Observable

interface IDataManager : PreferenceHelper, ApiHelper, DbHelper {

    fun fetchVideoDetail(url: String, videoId: Long): Observable<VideoDetail>
}