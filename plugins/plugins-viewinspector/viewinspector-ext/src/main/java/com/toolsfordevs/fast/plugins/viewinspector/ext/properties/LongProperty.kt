package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
open class LongProperty<T: View>(getter: (T.() -> Long?)? = null, setter: (T.(Long) -> Unit)? = null) : ViewProperty<T, Long>(getter,
    setter as (T.(Long?) -> Unit)?)
