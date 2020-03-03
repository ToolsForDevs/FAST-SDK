package com.toolsfordevs.fast.plugins.sharedprefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.util.AndroidVersion

@Suppress("UNCHECKED_CAST")
internal class PrefManager
{
    companion object
    {
        fun getSharedPreferences():SharedPreferences
        {
            return AppInstance.get().getSharedPreferences(Prefs.lastSelectedPreferenceFile, Context.MODE_PRIVATE)
        }

        fun addEntry(key:String, value:Any)
        {
            val prefs = getSharedPreferences().edit()

            when (value)
            {
                is Boolean -> prefs.putBoolean(key, value)
                is Int -> prefs.putInt(key, value)
                is Float -> prefs.putFloat(key, value)
                is Long -> prefs.putLong(key, value)
                is String -> prefs.putString(key, value)
                is HashSet<*> -> prefs.putStringSet(key, value as Set<String>)
            }

            prefs.apply()
        }

        fun deleteKey(key:String)
        {
            getSharedPreferences().edit().remove(key).apply()
        }

        @SuppressLint("NewApi")
        fun deleteCurrentFile()
        {
            if (AndroidVersion.isMinNougat())
                AppInstance.get().deleteSharedPreferences(Prefs.lastSelectedPreferenceFile)
            else
                getSharedPreferences().edit().clear().apply()

            Prefs.lastSelectedPreferenceFile = null // Delete pref as file is erased
        }
    }
}