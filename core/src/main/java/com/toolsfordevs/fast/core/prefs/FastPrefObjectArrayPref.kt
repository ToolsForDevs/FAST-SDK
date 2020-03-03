package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONArray

@Keep
class FastPrefObjectArrayPref<T : IFastPrefObject>(key: String, val factory: () -> T, defaultValue:List<T>) : JsonArrayTransformPref<List<T>>(key, defaultValue)
{
    override fun read(jsonArray: JSONArray): List<T>
    {
        return arrayListOf<T>().apply {
            for (i in 0 until jsonArray.length()) add(factory().apply { fromJSON(jsonArray.getJSONObject(i)) })
        }
    }

    override fun write(data: List<T>): JSONArray
    {
        return JSONArray().apply {
            for (item in data) put(item.toJSON())
        }
    }
}