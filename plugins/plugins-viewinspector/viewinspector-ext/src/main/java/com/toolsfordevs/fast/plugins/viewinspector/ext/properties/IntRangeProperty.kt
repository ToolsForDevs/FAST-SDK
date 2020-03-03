package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KMutableProperty1

@Keep
open class IntRangeProperty<T: View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null, val min:Int = 0, val max:Int = 100) : IntProperty<T>(getter, setter)
{
    constructor(property: KMutableProperty1<T, Int>, min:Int = 0, max:Int = 100) : this(property.getter, property.setter, min, max)
    open fun getMinimum() = min
    open fun getMaximum() = max
}