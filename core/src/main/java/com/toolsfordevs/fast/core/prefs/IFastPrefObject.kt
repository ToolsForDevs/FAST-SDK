package com.toolsfordevs.fast.core.prefs

import org.json.JSONArray
import org.json.JSONObject


interface IFastPrefObject
{
    fun toJSON():JSONObject
    fun fromJSON(json:JSONObject)

    fun toJsonArray(list:Collection<IFastPrefObject>):JSONArray
    {
        return JSONArray().apply {
            for (iFastPrefObject in list)
            {
                put(iFastPrefObject.toJSON())
            }
        }
    }

    fun fromJsonArray(array:JSONArray):List<JSONObject>
    {
        return arrayListOf<JSONObject>().apply {
            for (i in 0 until array.length())
                add(array.getJSONObject(i))
        }
    }
}