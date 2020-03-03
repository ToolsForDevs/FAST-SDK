package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
class IntPref(private val key:String, private val defaultValue:Int)
{
    // ToDo : add possibility to add a listener to listen for value changes (basically in setValue)
    private var initialized = false
    private var value:Int = 0

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)!!
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}