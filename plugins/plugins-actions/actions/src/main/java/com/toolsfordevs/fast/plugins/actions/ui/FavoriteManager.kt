package com.toolsfordevs.fast.plugins.actions.ui

import org.json.JSONArray


internal object FavoriteManager
{
    private val jsonArray: JSONArray by lazy { Prefs.favoriteActions ?: JSONArray() }

    private val listeners = arrayListOf<() -> Unit>()

    fun toggleFavorite(actionId: String, starred: Boolean)
    {
        if (!starred)
        {
            jsonArray.put(actionId)
        }
        else
        {
            for (i in 0 until jsonArray.length())
            {
                if (jsonArray.getString(i) == actionId)
                {
                    jsonArray.remove(i)
                    break
                }
            }
        }

        Prefs.favoriteActions = jsonArray

        for (listener in listeners) listener.invoke()
    }

    fun isFavorite(actionId: String): Boolean
    {
        for (i in 0 until jsonArray.length())
        {
            if (jsonArray.getString(i) == actionId) return true
        }

        return false
    }

    fun addOnFavoriteChangeCallback(callback: () -> Unit)
    {
        listeners.add(callback)
    }

    fun removeOnFavoriteChangeCallback(callback: () -> Unit)
    {
        listeners.remove(callback)
    }
}