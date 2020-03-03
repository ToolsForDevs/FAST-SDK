package com.toolsfordevs.fast.plugins.viewinspector.modules.drawerlayout.widgets

import androidx.drawerlayout.widget.DrawerLayout
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionFloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class DrawerLayoutProperties: ViewPropertyHolder(DrawerLayout::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(DrawerLayout::getDrawerElevation,
                DrawerLayout::setDrawerElevation,
                max = dp(50f)))
        // setDrawerLockMode no getter :(
        // add(PropertyCategory.COMMON, ColorProperty(DrawerLayout::getScri, DrawerLayout::setScrimColor)) no getter :(
        add(PropertyCategory.COMMON,
            DrawableProperty(DrawerLayout::getStatusBarBackgroundDrawable,
                DrawerLayout::setStatusBarBackground))
    }
}