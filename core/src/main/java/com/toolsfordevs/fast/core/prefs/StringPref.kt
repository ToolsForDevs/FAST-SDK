package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
class StringPref(private val key:String, private val defaultValue:String?)
{
    private var initialized = false
    private var value:String? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String?
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}