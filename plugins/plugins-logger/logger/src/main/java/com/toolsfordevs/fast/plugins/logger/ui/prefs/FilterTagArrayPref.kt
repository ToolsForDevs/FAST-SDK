package com.toolsfordevs.fast.plugins.logger.ui.prefs

import com.toolsfordevs.fast.core.prefs.JsonArrayTransformPref
import org.json.JSONArray
import org.json.JSONObject


internal class FilterTagArrayPref : JsonArrayTransformPref<List<TagFilter>>("PLUGIN_LOGGER_FILTER_TAG_LIST", listOf())
{
    override fun read(jsonArray: JSONArray): List<TagFilter>
    {
        val list = arrayListOf<TagFilter>()

        for (i in 0 until jsonArray.length())
        {
            val o = jsonArray.getJSONObject(i)
            val filter = o.getString("filter")
            val type = o.getString("type")

            list.add(TagFilter(filter, FilterType.valueOf(type)))
        }

        return list
    }

    override fun write(data: List<TagFilter>): JSONArray
    {
        val array = JSONArray()

        for (item in data)
        {
            val o = JSONObject()
            o.put("filter", item.filter)
            o.put("type", item.type.name)
            array.put(o)
        }

        return array
    }
}