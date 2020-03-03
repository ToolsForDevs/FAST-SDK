package com.toolsfordevs.fast.modules.resourcepicker.prefs

import com.toolsfordevs.fast.core.prefs.JsonArrayTransformPref
import com.toolsfordevs.fast.modules.resources.*
import org.json.JSONArray
import org.json.JSONObject

internal class ValueResourceArrayPref<T : ValueResource<*>>(private val key: String) : JsonArrayTransformPref<ArrayList<T>>(key, arrayListOf())
{
    override fun read(jsonArray: JSONArray): ArrayList<T>
    {
        val data = arrayListOf<T>()

        for (i in 0 until jsonArray.length())
        {
            val o: JSONObject = jsonArray.getJSONObject(i)

            val type = o.getString("type")
            val name = o.getString("name")
            val resId = -1//o.getInt("resId")
            val hasValue = o.has("value")

            val res: T = when (type)
            {
                TypedResource.COLOR  -> ColorResource(name, resId, if (hasValue) o.getInt("value") else null)
                TypedResource.DIMEN  -> DimensionResource(name, resId, if (hasValue) o.getDouble("value").toFloat() else null)
                TypedResource.STRING -> StringResource(name, resId, if (hasValue) o.getString("value") else null)
//                TypedResource.STYLE -> StringResource(name, resId)
//                TypedResource.ARRAY -> StringResource(name, resId)
                else                 -> throw Exception("Couldn't find TypedResource type for $type")
            } as T

            // Do not add resources that do not longer exist
            if (res.resId > 0 || res.value != null)
                data.add(res)
        }

        return data
    }

    override fun write(data: ArrayList<T>): JSONArray
    {
        val array = JSONArray()

        for (resource in data)
        {
            // Do not add resources that do not longer exist
            if (resource.resId > 0 || resource.value != null)
            {
                val o = JSONObject()
                o.put("type", resource.type)
                o.put("name", resource.name)
                // Since resId might change in next compilation, do not store it
//                o.put("resId", resource.resId)

                if (resource.value != null)
                    o.put("value", resource.value)

                array.put(o)
            }
        }

        return array
    }
}