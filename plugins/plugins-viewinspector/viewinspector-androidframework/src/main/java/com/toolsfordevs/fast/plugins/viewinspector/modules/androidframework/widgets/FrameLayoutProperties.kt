package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.view.View
import android.widget.FrameLayout
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty


internal class FrameLayoutProperties : ViewPropertyHolder(FrameLayout::class.java)
{
    init
    {
        add(PropertyCategory.LAYOUT_PARAMS, FrameLayoutGravityProperty())
    }

    private class FrameLayoutGravityProperty : GravityProperty<View>()
    {
        override fun getValue(): Int
        {
            val params = view.layoutParams as FrameLayout.LayoutParams
            return params.gravity
        }

        override fun setValue(value: Int?):Boolean
        {
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = value!!
            view.layoutParams = params

            return true
        }
    }
}