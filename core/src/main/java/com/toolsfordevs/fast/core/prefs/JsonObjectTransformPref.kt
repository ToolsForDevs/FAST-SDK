package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep
import org.json.JSONObject
import kotlin.reflect.KProperty

@Keep
abstract class JsonObjectTransformPref<T>(key: String, val defaultValue: T)
{
    private var initialized = false
    private var value: T? = null

    private var prefValue: JSONObject? by JsonObjectPref(key)

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

    protected abstract fun read(jsonObject: JSONObject): T

    protected abstract fun write(data: T): JSONObject
}