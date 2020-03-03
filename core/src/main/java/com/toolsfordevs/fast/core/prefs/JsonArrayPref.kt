package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONArray
import kotlin.reflect.KProperty

@Keep
class JsonArrayPref(private val key:String)
{
    private var initialized = false
    private var value:JSONArray? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): JSONArray?
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, null)
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: JSONArray?)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}