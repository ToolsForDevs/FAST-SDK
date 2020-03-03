package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.FloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.IntRangeProperty


open class AngleProperty<T: View>(getter: (T.() -> Float)? = null, setter: (T.(Float) -> Unit)? = null) : FloatRangeProperty<T>(getter, setter, 0f, 360f)
@Keep
open class AngleIntProperty<T: View>(getter: (T.() -> Int)? = null, setter: (T.(Int) -> Unit)? = null) : IntRangeProperty<T>(getter, setter, 0, 360)
