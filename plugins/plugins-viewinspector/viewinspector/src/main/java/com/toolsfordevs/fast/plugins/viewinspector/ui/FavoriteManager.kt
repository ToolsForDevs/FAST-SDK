package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.view.View
import org.json.JSONObject


internal object FavoriteManager
{
    private val jsonObject: JSONObject by lazy { JSONObject(Prefs.favoriteProperties) }

    private val listeners = arrayListOf<() -> Unit>()

    fun toggleFavorite(view: View, propertyId: String, starred: Boolean)
    {
        val key = view::javaClass.get().simpleName

        if (!starred)
        {
            jsonObject.accumulate(key, propertyId)
        }
        else
        {
            val array = jsonObject.optJSONArray(key)

            array?.let {
                for (i in 0 until array.length())
                {
                    if (array.getString(i) == propertyId)
                    {
                        array.remove(i)
                        break
                    }
                }
            }
        }

        Prefs.favoriteProperties = jsonObject.toString()

        for (listener in listeners)
            listener.invoke()
    }

    fun isFavorite(view: View, propertyId: String): Boolean
    {
        val key = view::javaClass.get().simpleName
        val array = jsonObject.optJSONArray(key)

        array?.let {
            for (i in 0 until array.length())
            {
                if (array.getString(i) == propertyId)
                    return true
            }
        }

        return false
    }

    fun addOnFavoriteChangeCallback(callback: () -> Unit)
    {
        listeners.add(callback)
    }

    fun removeOnFavoriteChangeCallback(callback:() -> Unit)
    {
        listeners.remove(callback)
    }
}