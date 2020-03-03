package com.toolsfordevs.fast.plugins.sessionmanager.prefs

import com.toolsfordevs.fast.core.prefs.JsonObjectTransformPref
import com.toolsfordevs.fast.core.util.FastSort
import org.json.JSONObject

internal class SortTypePrefs : JsonObjectTransformPref<HashMap<String, String>>("PLUGIN_SESSION_MANAGER_TABS_SORT_TYPE", hashMapOf())
{
    override fun read(jsonObject: JSONObject): HashMap<String, String>
    {
        val map = hashMapOf<String, String>()

        for (key in jsonObject.keys())
            map[key] = jsonObject.optString(key, FastSort.DEFAULT)

        return map
    }

    override fun write(data: HashMap<String, String>): JSONObject
    {
        return JSONObject().apply { data.forEach { entry -> put(entry.key, entry.value) } }
    }

}