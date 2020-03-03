package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
open class FloatProperty<T: View>(getter: (T.() -> Float?)? = null, setter: (T.(Float?) -> Unit)? = null, val decimalScale:Int = 2) : ViewProperty<T, Float>(getter, setter)
