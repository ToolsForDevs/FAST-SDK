package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONArray

@Keep
class IntArrayPref(key:String) : JsonArrayTransformPref<List<Int>>(key, listOf())
{
    override fun read(jsonArray: JSONArray): List<Int>
    {
        return arrayListOf<Int>().apply {
            for (i in 0 until jsonArray.length())
                add(jsonArray.getInt(i))
        }
    }

    override fun write(data: List<Int>): JSONArray
    {
        return JSONArray().apply {
            for (item in data)
                put(item)
        }
    }
}