package com.toolsfordevs.fast.core.prefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.toolsfordevs.fast.core.AppInstance
import org.json.JSONArray
import org.json.JSONObject


internal class FastPreferences private constructor(delegate: PrefProvider) : PrefProvider by delegate
{
    companion object : PrefProvider
    {
        private var _instance: FastPreferences? = null

        private val instance: FastPreferences
            get()
            {
                if (_instance == null)
                    _instance = makeDefaultInstance()

                return _instance!!
            }

        private fun makeDefaultInstance(): FastPreferences
        {
            return FastPreferences(SharedPrefsProvider())
        }

        internal fun initialize(delegate: PrefProvider)
        {
            _instance = FastPreferences(delegate)
        }

        /**
         * Supported value types : String, Boolean, Integer, Long, Double
         */
        override fun <T> put(key: String, value: T?): Boolean
        {
            // Log.d("FastPrefs", "put value $value for $key")

            if (value == null)
            {
                remove(key)
                return true
            }

            return instance.put(key, value)
        }

        override fun <T> get(key: String, defaultValue: T?): T?
        {
            // Log.d("FastPrefs", "get value for $key")

            return instance.get(key, defaultValue)
        }

        override fun remove(key: String)
        {
            instance.remove(key)
        }

        override fun fromJson(json: JSONObject)
        {
            instance.fromJson(json)
        }

        override fun toJson(): JSONObject
        {
            return instance.toJson()
        }
    }
}

interface PrefProvider
{
    fun <T> put(key: String, value: T?): Boolean
    fun <T> get(key: String, defaultValue: T?): T?
    fun remove(key: String)
    fun fromJson(json: JSONObject)
    fun toJson(): JSONObject
}

class SharedPrefsProvider : PrefProvider
{
    companion object
    {
        private const val FAST_PREF_FILENAME = "FAST"

        val ignoredFiles = arrayListOf(FAST_PREF_FILENAME)
    }

    private val preferences: SharedPreferences = AppInstance.get().getSharedPreferences(FAST_PREF_FILENAME, Context.MODE_PRIVATE
    )

    override fun <T> put(key: String, value: T?): Boolean
    {
        val editor = preferences.edit()

        when (value)
        {
            is String     -> editor.putString(key, "0$value")
            is Boolean    -> editor.putString(key, "1$value")
            is Float      -> editor.putString(key, "2$value")
            is Double     -> editor.putString(key, "2${value.toFloat()}")
            is Int        -> editor.putString(key, "3$value")
            is Long       -> editor.putString(key, "4$value")
            is JSONObject -> editor.putString(key, "5$value")
            is JSONArray  -> editor.putString(key, "6$value")
            else          -> return false
        }

        editor.apply()
        return true
    }

    override fun <T> get(key: String, defaultValue: T?): T?
    {
        val value: String = preferences.getString(key, null) ?: return defaultValue

        val type: Int = Integer.parseInt(value[0].toString())
        val userValue = value.substring(1)

        return when (type)
        {
            0    -> userValue
            1    -> userValue.toBoolean()
            2    -> userValue.toFloat()
            3    -> userValue.toInt()
            4    -> userValue.toLong()
            5    -> JSONObject(userValue)
            6    -> JSONArray(userValue)
            else -> null
        } as T?
    }

    override fun remove(key: String)
    {
        preferences.edit().remove(key).apply()
    }

    override fun fromJson(json: JSONObject)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toJson(): JSONObject
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}