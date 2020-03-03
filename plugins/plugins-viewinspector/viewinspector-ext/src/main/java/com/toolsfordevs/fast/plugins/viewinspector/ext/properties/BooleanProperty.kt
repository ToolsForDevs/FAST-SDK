package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

import kotlin.reflect.KMutableProperty1

@Keep
open class BooleanProperty<T: View>(getter: (T.() -> Boolean?)? = null, setter: (T.(Boolean) -> Unit)? = null) : ViewProperty<T, Boolean>(getter,
    setter as (T.(Boolean?) -> Unit)?
                                                                                                                                         )
{
    constructor(property:KMutableProperty1<T, Boolean>) : this(property.getter, property.setter)
}

// Couldn't I remove the view here ?
// Couldn't I extend the adapter to pass the view, which would then pass the view to the renderer ?
// Or set the view to attributes like attributes.view = view