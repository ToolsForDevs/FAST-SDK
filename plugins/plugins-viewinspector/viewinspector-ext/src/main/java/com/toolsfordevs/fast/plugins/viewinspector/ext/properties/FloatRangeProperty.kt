package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KMutableProperty1

@Keep
open class FloatRangeProperty<T: View>(getter: (T.() -> Float?)? = null, setter: (T.(Float) -> Unit)? = null, val min:Float = 0f, val max:Float = 100f, decimalScale:Int = 2) : FloatProperty<T>(getter,
    setter as (T.(Float?) -> Unit)?, decimalScale)
{
    constructor(property: KMutableProperty1<T, Float>, min:Float = 0f, max:Float = 100f, decimalScale:Int = 2) : this(property.getter, property.setter, min, max, decimalScale)
    open fun getMinimum() = min
    open fun getMaximum() = max
}