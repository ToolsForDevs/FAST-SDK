package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.util.ArrayMap
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
abstract class SingleChoiceProperty<T: View, R>(getter: (T.() -> R?)? = null, setter: (T.(R) -> Unit)? = null) : ViewProperty<T, R>(getter, setter)
{
    //constructor(getter: (T.() -> R?)? = null, setter: (T.(R) -> Unit)? = null):this(getter, setter as T.(R?) -> Unit)

    var map: ArrayMap<R, String> = ArrayMap()
        get()
        {
            if (field.isEmpty())
                defineKeyValues(field)

            return field
        }
    abstract fun defineKeyValues(map:ArrayMap<R, String>)

    // Could do better
    // use getter and setter
    // replace getSelectedIndex, setSelectedIndex and getLabels by map<R, String>
    // if no value, default to index 0
    // This stuff id done in renderer
}