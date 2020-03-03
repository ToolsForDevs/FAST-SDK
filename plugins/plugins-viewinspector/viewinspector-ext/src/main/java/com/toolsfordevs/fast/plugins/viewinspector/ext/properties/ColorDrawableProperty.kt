package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.graphics.drawable.Drawable
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import kotlin.reflect.KMutableProperty1

@Keep
class ColorDrawableProperty<T: View>(getter: (T.() -> Drawable?)? = null, setter: (T.(Drawable?) -> Unit)? = null) : ViewProperty<T, Drawable>(getter, setter)
{
    constructor(property: KMutableProperty1<T, Drawable?>) : this(property.getter, property.setter)
}