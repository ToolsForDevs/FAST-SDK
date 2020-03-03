package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
open class NumberRangeProperty<T: View, R:Number>(getter: (T.() -> R?)? = null, setter: (T.(R) -> Unit)? = null, val min:R, val max:R) : ViewProperty<T, R>(getter, setter)
{
    open fun getMinimum() = min
    open fun getMaximum() = max
}