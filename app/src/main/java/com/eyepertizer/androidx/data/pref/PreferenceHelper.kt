package com.eyepertizer.androidx.data.pref

interface PreferenceHelper {

    fun getFirstEntryApp(): Boolean

    fun setFirstEntryApp(isFirst: Boolean)

    fun setUUID(uuid: String)

    fun getUUID(): String?

}