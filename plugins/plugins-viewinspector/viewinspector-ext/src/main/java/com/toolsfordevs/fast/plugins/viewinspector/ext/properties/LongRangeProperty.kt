package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class LongRangeProperty<T: View>(getter: (T.() -> Long?)? = null, setter: (T.(Long) -> Unit)? = null, val min:Long = 0, val max:Long = 100) : LongProperty<T>(getter, setter)
{
    open fun getMinimum() = min
    open fun getMaximum() = max
}