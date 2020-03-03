package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.google.android.material.tabs.TabLayout


internal class TabLayoutProperties: ViewPropertyHolder(TabLayout::class.java)
{
    init
    {
        // setElevation is in SuperClass
        add(PropertyCategory.COMMON,
            BooleanProperty(TabLayout::isInlineLabel, TabLayout::setInlineLabel))
         add(PropertyCategory.COMMON,
             DrawableProperty(TabLayout::getTabSelectedIndicator,
                 TabLayout::setSelectedTabIndicator))
        // setSelectedTabIndicatorColor // ToDo merge with colordrawable picker
         add(PropertyCategory.COMMON, SelectedTabIndicatorGravityProperty())
        // add(PropertyCategory.COMMON, DimensionIntRangeProperty(TabLayout::getTabIconTint, TabLayout::setSelectedTabIndicatorHeight)) no getter :(
        add(PropertyCategory.COMMON, TabGravityProperty())
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TabLayout::getTabIconTint,
                TabLayout::setTabIconTint))
        add(PropertyCategory.COMMON,
            BooleanProperty(TabLayout::isTabIndicatorFullWidth,
                TabLayout::setTabIndicatorFullWidth))
        // setTabMode
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TabLayout::getTabRippleColor,
                TabLayout::setTabRippleColor))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TabLayout::getTabTextColors,
                TabLayout::setTabTextColors))
        add(PropertyCategory.COMMON,
            BooleanProperty(TabLayout::hasUnboundedRipple,
                TabLayout::setUnboundedRipple))
    }

    private class TabGravityProperty : SingleChoiceProperty<TabLayout, Int>(TabLayout::getTabGravity, TabLayout::setTabGravity)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(TabLayout.GRAVITY_CENTER, "CENTER")
            map.put(TabLayout.GRAVITY_FILL, "FILL")
        }
    }

    private class SelectedTabIndicatorGravityProperty : SingleChoiceProperty<TabLayout, Int>(TabLayout::getTabIndicatorGravity, TabLayout::setSelectedTabIndicatorGravity)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(TabLayout.INDICATOR_GRAVITY_BOTTOM, "BOTTOM")
            map.put(TabLayout.INDICATOR_GRAVITY_CENTER, "CENTER")
            map.put(TabLayout.INDICATOR_GRAVITY_STRETCH, "STRETCH")
            map.put(TabLayout.INDICATOR_GRAVITY_TOP, "TOP")
        }
    }
}