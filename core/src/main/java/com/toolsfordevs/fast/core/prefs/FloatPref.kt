package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
class FloatPref(private val key:String, private val defaultValue:Float)
{
    private var initialized = false
    private var value:Float = 0f

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Float
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)!!
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)
    }
}