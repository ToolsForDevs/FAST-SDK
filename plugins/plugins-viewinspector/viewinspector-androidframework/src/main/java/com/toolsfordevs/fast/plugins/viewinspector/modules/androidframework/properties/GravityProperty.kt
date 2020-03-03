package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties

import android.util.ArrayMap
import android.view.Gravity
import android.view.View
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.MultiChoiceProperty


open class GravityProperty<T: View>(getter: (T.() -> Int)? = null, setter: (T.(Int) -> Unit)? = null) : MultiChoiceProperty<T, Int>(getter, setter) {
    override fun defineKeyValues(map: ArrayMap<Int, String>)
    {
        map.put(Gravity.NO_GRAVITY, "None")
        map.put(Gravity.BOTTOM, "Bottom")
        map.put(Gravity.CENTER, "Center")
        map.put(Gravity.CENTER_HORIZONTAL, "Center horizontal")
        map.put(Gravity.CENTER_VERTICAL, "Center vertical")
        map.put(Gravity.END, "End")
        map.put(Gravity.LEFT, "Left")
        map.put(Gravity.RIGHT, "Right")
        map.put(Gravity.START, "Start")
        map.put(Gravity.TOP, "Top")
    }

    init
    {
        name = "Gravity"
    }

    override fun getLabel(): String {
        if (getSelectedValues().isEmpty())
            return "NONE"

        var label = ""

        for (flag in getSelectedValues()) {
            if (label.isNotEmpty())
                label += "|"

            label += map[flag]
        }

        return label
    }

    override fun getSelectedValues(): List<Int> {
        val gravity = getValue()!!

        val list = arrayListOf<Int>()

        if (gravity != -1) {
            val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK
            if (verticalGravity in listOf(Gravity.TOP, Gravity.CENTER_VERTICAL, Gravity.BOTTOM))
                list.add(verticalGravity)

            val absoluteGravity = Gravity.getAbsoluteGravity(gravity, view.parent.layoutDirection)
            val horizontalGravity = absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK

            if (horizontalGravity in listOf(Gravity.LEFT, Gravity.CENTER_HORIZONTAL, Gravity.RIGHT))
                list.add(horizontalGravity)
        }

        return list
    }

    override fun setSelectedValues(values: List<Int>) {
        var gravity = -1

        if (values.isNotEmpty()) {
            gravity = 0

            for (flag in values)
                gravity = gravity or flag
        }

        setValue(gravity)
    }
}