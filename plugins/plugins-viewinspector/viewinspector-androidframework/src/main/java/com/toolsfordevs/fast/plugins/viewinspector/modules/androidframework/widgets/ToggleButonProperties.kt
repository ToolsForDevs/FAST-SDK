package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.ToggleButton
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.StringProperty


internal class ToggleButonProperties: ViewPropertyHolder(ToggleButton::class.java)
{
    init
    {
        // Checked in superclass
        add(PropertyCategory.COMMON, StringProperty(ToggleButton::getTextOn, ToggleButton::setTextOn))
        add(PropertyCategory.COMMON, StringProperty(ToggleButton::getTextOff, ToggleButton::setTextOff))
    }
}