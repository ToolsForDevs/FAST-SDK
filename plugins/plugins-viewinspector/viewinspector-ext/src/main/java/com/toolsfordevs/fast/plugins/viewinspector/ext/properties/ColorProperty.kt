package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.ArrayMap
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty

@Keep
open class ColorProperty<T: View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null) : IntProperty<T>(getter, setter)

@Keep
open class ColorStateListProperty<T: View>(getter: (T.() -> ColorStateList?)? = null, setter: (T.(ColorStateList?) -> Unit)? = null) : ViewProperty<T, ColorStateList>(getter, setter)
{
    override fun hasValue(): Boolean = getter?.invoke(view) != null
}

@Keep
open class ColorTintModeProperty<T:View>(getter: (T.() -> PorterDuff.Mode?)? = null, setter: (T.(PorterDuff.Mode?) -> Unit)? = null) : SingleChoiceProperty<T, PorterDuff.Mode>(getter, setter) {
    override fun defineKeyValues(map: ArrayMap<PorterDuff.Mode, String>) {
        map.put(null, "UNDEFINED")
        map.put(PorterDuff.Mode.ADD, "ADD")
        map.put(PorterDuff.Mode.CLEAR, "CLEAR")
        map.put(PorterDuff.Mode.DARKEN, "DARKEN")
        map.put(PorterDuff.Mode.DST, "DST")
        map.put(PorterDuff.Mode.DST_ATOP, "DST_ATOP")
        map.put(PorterDuff.Mode.DST_IN, "DST_IN")
        map.put(PorterDuff.Mode.DST_OUT, "DST_OUT")
        map.put(PorterDuff.Mode.DST_OVER, "DST_OVER")
        map.put(PorterDuff.Mode.LIGHTEN, "LIGHTEN")
        map.put(PorterDuff.Mode.MULTIPLY, "MULTIPLY")
        map.put(PorterDuff.Mode.OVERLAY, "OVERLAY")
        map.put(PorterDuff.Mode.SCREEN, "SCREEN")
        map.put(PorterDuff.Mode.SRC, "SRC")
        map.put(PorterDuff.Mode.SRC_ATOP, "SRC_ATOP")
        map.put(PorterDuff.Mode.SRC_IN, "SRC_IN")
        map.put(PorterDuff.Mode.SRC_OUT, "SRC_OUT")
        map.put(PorterDuff.Mode.SRC_OVER, "SRC_OVER")
        map.put(PorterDuff.Mode.XOR, "XOR")
    }
}