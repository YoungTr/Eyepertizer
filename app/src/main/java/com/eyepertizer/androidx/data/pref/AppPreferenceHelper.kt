package com.eyepertizer.androidx.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.eyepertizer.androidx.di.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferenceHelper @Inject constructor(context: Context, @PreferenceInfo name: String) :
    PreferenceHelper {

    companion object {
        private const val PREF_KEY_FIRST_ENTRY_APP = "PREF_KEY_FIRST_ENTRY_APP"
        private const val PREF_KEY_UUID = "PREF_KEY_UUID"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun getFirstEntryApp(): Boolean = mPrefs.getBoolean(PREF_KEY_FIRST_ENTRY_APP, true)

    override fun setFirstEntryApp(isFirst: Boolean) = mPrefs.edit {
        putBoolean(PREF_KEY_FIRST_ENTRY_APP, isFirst)
    }

    override fun setUUID(uuid: String) {
        mPrefs.edit {
            putString(PREF_KEY_UUID, uuid)
        }
    }

    override fun getUUID(): String? {
        return mPrefs.getString(PREF_KEY_UUID, "")
    }


}