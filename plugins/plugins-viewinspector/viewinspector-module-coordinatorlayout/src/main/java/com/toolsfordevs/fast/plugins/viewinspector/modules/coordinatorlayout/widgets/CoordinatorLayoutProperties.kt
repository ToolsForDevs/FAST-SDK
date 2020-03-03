package com.toolsfordevs.fast.plugins.viewinspector.modules.coordinatorlayout.widgets

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class CoordinatorLayoutProperties: ViewPropertyHolder(CoordinatorLayout::class.java)
{
    init
    {
        // setFitsSystemWindow is in View
        add(PropertyCategory.COMMON,
            DrawableProperty(CoordinatorLayout::getStatusBarBackground,
                CoordinatorLayout::setStatusBarBackground))
        // setVisibility is in View
    }
}