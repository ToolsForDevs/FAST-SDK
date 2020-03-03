package com.toolsfordevs.fast.plugins.logger.ui.prefs

import com.toolsfordevs.fast.core.prefs.JsonArrayTransformPref
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


internal class FilterProfileArrayPref : JsonArrayTransformPref<List<FilterProfile>>("PLUGIN_LOGGER_FILTER_PROFILES", listOf())
{
    override fun read(jsonArray: JSONArray): List<FilterProfile>
    {
        val list = arrayListOf<FilterProfile>()

        for (i in 0 until jsonArray.length())
        {
            val o = jsonArray.getJSONObject(i)

            val id = o.getString("id")
            val name = o.getString("name")

            val priorities = o.getJSONArray("priorities")
            val tags = o.getJSONArray("tags")

            list.add(FilterProfile(id, name, readPriorities(priorities), readTags(tags)))
        }

        return list
    }

    private fun readPriorities(jsonArray: JSONArray):HashSet<Int>
    {
        val set = hashSetOf<Int>()

        for (i in 0 until jsonArray.length())
            set.add(jsonArray.getInt(i))

        return set
    }

    private fun readTags(jsonArray: JSONArray):ArrayList<TagFilter>
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

    override fun write(data: List<FilterProfile>): JSONArray
    {
        val array = JSONArray()

        for (item in data)
        {
            val o = JSONObject()
            o.put("id", item.id)
            o.put("name", item.name)
            o.put("priorities", writePriorities(item.priorities))
            o.put("tags", writeTags(item.tags))
            array.put(o)
        }

        return array
    }

    private fun writePriorities(priorities:Set<Int>):JSONArray
    {
        val a = JSONArray()

        for (p in priorities)
            a.put(p)

        return a
    }


    private fun writeTags(tags:List<TagFilter>):JSONArray
    {
        val a = JSONArray()

        for (tag in tags)
        {
            if (tag.filter.isNotBlank())
            {
                val o = JSONObject()
                o.put("filter", tag.filter)
                o.put("type", tag.type.name)

                a.put(o)
            }
        }

        return a
    }
}