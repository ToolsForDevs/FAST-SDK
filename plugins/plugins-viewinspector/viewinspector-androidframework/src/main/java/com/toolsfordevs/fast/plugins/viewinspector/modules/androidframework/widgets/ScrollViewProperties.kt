package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.ScrollView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class ScrollViewProperties: ViewPropertyHolder(ScrollView::class.java)
{
    init
    {
        // Android Q ToDo
//        add(PropertyCategory.COMMON, ColorProperty(ScrollView::getBottomEdgeEffectColor, ScrollView::setBottomEdgeEffectColor))
//        add(PropertyCategory.COMMON, ColorProperty(ScrollView::getEdgeEffectColor, ScrollView::setEdgeEffectColor))
        add(PropertyCategory.COMMON, BooleanProperty(ScrollView::isFillViewport, ScrollView::setFillViewport))
        add(PropertyCategory.COMMON, BooleanProperty(ScrollView::isSmoothScrollingEnabled, ScrollView::setSmoothScrollingEnabled))
        // Android Q
//        add(PropertyCategory.COMMON, ColorProperty(ScrollView::getTopEdgeEffectColor, ScrollView::setTopEdgeEffectColor))
    }
}