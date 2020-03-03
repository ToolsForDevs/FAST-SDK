package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties

import android.view.View
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.FloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.IntRangeProperty


class AlphaProperty<T: View>(getter: (T.() -> Float)? = null, setter: (T.(Float) -> Unit)? = null) : FloatRangeProperty<T>(getter, setter, 0f, 1f)
class AlphaIntProperty<T: View>(getter: (T.() -> Int)? = null, setter: (T.(Int) -> Unit)? = null) : IntRangeProperty<T>(getter, setter, 0, 255)
