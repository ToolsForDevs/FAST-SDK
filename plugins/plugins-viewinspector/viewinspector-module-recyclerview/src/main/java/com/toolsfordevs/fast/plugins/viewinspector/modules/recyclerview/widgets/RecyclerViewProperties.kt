package com.toolsfordevs.fast.plugins.viewinspector.modules.recyclerview.widgets

import androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class RecyclerViewProperties: ViewPropertyHolder(RecyclerView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(RecyclerView::getClipToPadding,
                RecyclerView::setClipToPadding))
        add(PropertyCategory.COMMON,
            BooleanProperty(RecyclerView::hasFixedSize,
                RecyclerView::setHasFixedSize))
        // add(PropertyCategory.COMMON, IntRangeProperty(RecyclerView::itemvisLayoutFrozen, RecyclerView::setItemViewCacheSize, max = 50)) no getter :(
        add(PropertyCategory.COMMON, BooleanProperty(
            RecyclerView::isLayoutFrozen,
            RecyclerView::setLayoutFrozen).apply { deprecatedApi = "D"})
        //setLayoutManager // ToDo
        //setLayoutTransition ? deprecated
        add(PropertyCategory.COMMON,
            BooleanProperty(RecyclerView::isNestedScrollingEnabled,
                RecyclerView::setNestedScrollingEnabled))
        add(PropertyCategory.COMMON,
            BooleanProperty(RecyclerView::getPreserveFocusAfterLayout,
                RecyclerView::setPreserveFocusAfterLayout))
        // add(PropertyCategory.COMMON, IntRangeProperty(RecyclerView::touchs, RecyclerView::setScrollingTouchSlop)) no getter :(
        add(PropertyCategory.COMMON,
            BooleanProperty(RecyclerView::isLayoutSuppressed,
                RecyclerView::suppressLayout))
    }
}