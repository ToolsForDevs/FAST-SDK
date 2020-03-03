package com.toolsfordevs.fast.modules.resourcepicker.prefs

import com.toolsfordevs.fast.core.prefs.JsonArrayTransformPref
import com.toolsfordevs.fast.modules.resources.*
import org.json.JSONArray
import org.json.JSONObject

internal class TypedResourceArrayPref<T : TypedResource>(key: String) : JsonArrayTransformPref<ArrayList<T>>(key, arrayListOf())
{
    override fun read(jsonArray: JSONArray): ArrayList<T>
    {
        val data = arrayListOf<T>()

        for (i in 0 until jsonArray.length())
        {
            val o: JSONObject = jsonArray.getJSONObject(i)

            val type = o.getString("type")
            val name = o.getString("name")
            val resId = -1//o.optInt("resId")

            val res = when (type)
            {
                TypedResource.COLOR    -> ColorResource(name, resId)
                TypedResource.DIMEN    -> DimensionResource(name, resId)
                TypedResource.DRAWABLE -> DrawableResource(name)
                TypedResource.STRING   -> StringResource(name, resId)
                else                   -> throw Exception("Couldn't find TypedResource type for $type")
            } as T

            // Do not add resources that do not longer exist
            if (res.resId > 0)
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
            if (resource.resId > 0)
            {
                val o = JSONObject()
                o.put("type", resource.type)
                o.put("name", resource.name)
                // Since resId can be different in next compilation, don't store it
//                o.put("resId", resource.resId)
                array.put(o)
            }
        }

        return array
    }
}