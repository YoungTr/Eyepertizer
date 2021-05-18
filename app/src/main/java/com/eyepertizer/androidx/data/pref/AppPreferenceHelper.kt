package com.eyepertizer.androidx.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferenceHelper constructor(context: Context, name: String) :
    PreferenceHelper {

    companion object {
        private const val PREF_KEY_FIRST_ENTRY_APP = "PREF_KEY_FIRST_ENTRY_APP"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun getFirstEntryApp(): Boolean = mPrefs.getBoolean(PREF_KEY_FIRST_ENTRY_APP, true)

    override fun setFirstEntryApp(isFirst: Boolean) = mPrefs.edit {
        putBoolean(PREF_KEY_FIRST_ENTRY_APP, isFirst)
    }


}