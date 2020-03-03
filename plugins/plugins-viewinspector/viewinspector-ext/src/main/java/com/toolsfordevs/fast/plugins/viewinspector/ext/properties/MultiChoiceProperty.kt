package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.util.ArrayMap
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
abstract class MultiChoiceProperty<T: View, R>(getter: (T.() -> R?)? = null, setter: (T.(R) -> Unit)? = null) : ViewProperty<T, R>(getter, setter)
{
    abstract fun getLabel():String
    abstract fun getSelectedValues(): List<R>
    abstract fun setSelectedValues(values: List<R>)

    var map: ArrayMap<R, String> = ArrayMap()
        get()
        {
            if (field.isEmpty())
                defineKeyValues(field)

            return field
        }
    abstract fun defineKeyValues(map: ArrayMap<R, String>)
}

/**
 * A MultiChoice helper for properties that are base on bitwise int flags
 * For example, value = MyCLASS.FLAG1 | MyClass.FLAG2 ( "|" would be replaced by "or" in kotlin)
 * If you have more complex flag computation, you can override getSelectedValues and setSelectedValues to get the right behavior
 * If no values is selected, default value will be the first in the map key/values
 * For example, if you want a default value of -1, set a key with "None" associated with a -1 value as the first entry in the map
 */
@Keep
abstract class BitwiseMultiChoiceProperty<T:View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null) : MultiChoiceProperty<T, Int>(getter, setter)
{
    override fun getLabel(): String
    {
        var label = ""

        for (flag in getSelectedValues()) {
            if (label.isNotEmpty())
                label += "|"

            label += map[flag]
        }

        return label
    }

    override fun getSelectedValues(): List<Int> {
        val flags = getValue()!!

        val list = arrayListOf<Int>()

        for (flag in map.keys)
        {
            if (flag != 0 && (flags and flag) == flag)
                list.add(flag)
        }

        if (list.isEmpty())
            list.add(0)

        return list
    }

    override fun setSelectedValues(values: List<Int>) {
        var flags = -1

        if (values.isNotEmpty()) {
            flags = 0

            for (flag in values)
                flags = flags or flag
        }

        setValue(flags)
    }
}