package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.LayoutParamWHProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class ViewGroupProperties : ViewPropertyHolder(ViewGroup::class.java)
{
    init
    {
        add(PropertyCategory.LAYOUT_PARAMS, LayoutParamWHProperty<View>("layoutWidth", ViewGroup.LayoutParams::width, screenWidth))
        add(PropertyCategory.LAYOUT_PARAMS, LayoutParamWHProperty<View>("layoutHeight", ViewGroup.LayoutParams::height, screenHeight))
    }
}