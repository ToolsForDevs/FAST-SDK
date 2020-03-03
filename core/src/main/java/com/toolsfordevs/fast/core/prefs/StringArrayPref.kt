package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONArray

@Keep
class StringArrayPref(key:String) : JsonArrayTransformPref<List<String>>(key, listOf())
{
    override fun read(jsonArray: JSONArray): List<String>
    {
        return arrayListOf<String>().apply {
            for (i in 0 until jsonArray.length())
                add(jsonArray.getString(i))
        }
    }

    override fun write(data: List<String>): JSONArray
    {
        return JSONArray().apply {
            for (item in data)
                put(item)
        }
    }
}