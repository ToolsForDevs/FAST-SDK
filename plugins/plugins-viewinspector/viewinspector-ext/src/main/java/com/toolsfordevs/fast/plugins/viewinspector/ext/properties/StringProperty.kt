package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
class StringProperty<T: View>(getter: (T.() -> CharSequence?)? = null, setter: (T.(CharSequence?) -> Unit)? = null) : ViewProperty<T, CharSequence>(getter, setter)