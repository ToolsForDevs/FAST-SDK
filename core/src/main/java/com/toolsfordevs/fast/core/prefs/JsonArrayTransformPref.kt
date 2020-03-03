package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONArray
import kotlin.reflect.KProperty

@Keep
abstract class JsonArrayTransformPref<T>(key: String, val defaultValue: T)
{
    private var initialized = false
    private var value: T? = null

    private var prefValue: JSONArray? by JsonArrayPref(key)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    {
        if (!initialized)
        {
            value = if (prefValue != null) read(prefValue!!) else defaultValue
            initialized = true
        }
        return value!!
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    {
        this.value = value
        initialized = true
        prefValue = write(value)
    }

    protected abstract fun read(jsonArray: JSONArray): T

    protected abstract fun write(data: T): JSONArray
}