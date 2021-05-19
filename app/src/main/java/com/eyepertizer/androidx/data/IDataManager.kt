package com.eyepertizer.androidx.data

import com.eyepertizer.androidx.data.db.DbHelper
import com.eyepertizer.androidx.data.network.ApiHelper
import com.eyepertizer.androidx.data.pref.PreferenceHelper

interface IDataManager : PreferenceHelper, ApiHelper, DbHelper {
}