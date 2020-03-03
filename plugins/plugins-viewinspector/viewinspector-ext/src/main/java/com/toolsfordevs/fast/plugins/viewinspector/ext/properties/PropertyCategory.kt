package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class PropertyCategory {
    companion object
    {
        const val UNDEFINED = ""
        const val ACCESSIBILITY = "ACCESSIBILITY"
        const val COMMON = "COMMON"
        const val DRAWING = "DRAWING"
        const val FOCUS = "FOCUS"
        const val LAYOUT = "LAYOUT"
        const val LAYOUT_PARAMS = "LAYOUT_PARAMS"
        const val MEASUREMENT = "MEASUREMENT"
        const val METHODS = "METHODS"
        const val SCROLLING = "SCROLLING"
        const val TEXT = "TEXT"
    }
}