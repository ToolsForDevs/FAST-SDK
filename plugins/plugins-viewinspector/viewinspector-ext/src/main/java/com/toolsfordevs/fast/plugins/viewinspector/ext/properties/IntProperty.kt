package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
open class IntProperty<T: View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null) : ViewProperty<T, Int>(getter,
    setter as (T.(Int?) -> Unit)?)
