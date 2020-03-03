package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.HorizontalScrollView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class HorizontalScrollViewProperties: ViewPropertyHolder(HorizontalScrollView::class.java)
{
    init
    {
        // Android Q ToDo
//        add(PropertyCategory.COMMON, ColorProperty(android.widget.HorizontalScrollView::getEdgeEffectColor, android.widget.HorizontalScrollView::setEdgeEffectColor))
        add(PropertyCategory.COMMON, BooleanProperty(HorizontalScrollView::isFillViewport, HorizontalScrollView::setFillViewport))
        // Android Q
//        add(PropertyCategory.COMMON, ColorProperty(android.widget.HorizontalScrollView::getLeftEdgeEffectColor, android.widget.HorizontalScrollView::setBottomEdgeEffectColor))
//        add(PropertyCategory.COMMON, ColorProperty(android.widget.HorizontalScrollView::getRightEdgeEffectColor, android.widget.HorizontalScrollView::setTopEdgeEffectColor))
        add(PropertyCategory.COMMON, BooleanProperty(HorizontalScrollView::isSmoothScrollingEnabled, HorizontalScrollView::setSmoothScrollingEnabled))
    }
}