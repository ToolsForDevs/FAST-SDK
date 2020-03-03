package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KProperty

@Keep
abstract class FastPref<T>(val key:String, private val defaultValue: T)
{
    protected val listeners = arrayListOf<FastPrefListener>()

    private var initialized = false
    private var value:T = defaultValue

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    {
        if (!initialized)
        {
            value = FastPreferences.get(key, defaultValue)!!
            initialized = true
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    {
        this.value = value
        initialized = true
        FastPreferences.put(key, value)

        for (listener in listeners)
            listener.onValueChange()
    }

    private fun addListener(listener: FastPrefListener)
    {
        listeners.add(listener)
    }

    private fun removeListener(listener: FastPrefListener)
    {
        listeners.remove(listener)
    }

    interface FastPrefListener
    {
        fun onValueChange()
    }
}