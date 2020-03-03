package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONObject
import kotlin.reflect.KProperty

@Keep
class JsonObjectPref(private val key:String)
{
    private var initialized = false
    private var value:JSONObject? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): JSONObject?
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, null)
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: JSONObject?)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}