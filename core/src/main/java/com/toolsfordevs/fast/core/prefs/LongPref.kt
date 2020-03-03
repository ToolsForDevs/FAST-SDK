package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
class LongPref(private val key:String, private val defaultValue:Long)
{
    private var initialized = false
    private var value:Long = 0L

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)!!
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}