package com.toolsfordevs.fast.plugins.viewinspector.modules.core.widgets

import androidx.core.widget.NestedScrollView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class NestedScrollViewProperties: ViewPropertyHolder(NestedScrollView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(NestedScrollView::isFillViewport,
                NestedScrollView::setFillViewport))
        add(PropertyCategory.COMMON,
            BooleanProperty(NestedScrollView::isNestedScrollingEnabled,
                NestedScrollView::setNestedScrollingEnabled))
        add(PropertyCategory.COMMON,
            BooleanProperty(NestedScrollView::isSmoothScrollingEnabled,
                NestedScrollView::setSmoothScrollingEnabled))
    }
}