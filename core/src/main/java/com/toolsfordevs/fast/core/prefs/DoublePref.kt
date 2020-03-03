package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
class DoublePref(private val key:String, private val defaultValue:Double)
{
    private var initialized = false
    private var value:Double = 0.0

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)!!
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}