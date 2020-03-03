package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.google.android.material.bottomappbar.BottomAppBar


internal class BottomAppBarProperties: ViewPropertyHolder(BottomAppBar::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            ColorStateListProperty(BottomAppBar::getBackgroundTint,
                BottomAppBar::setBackgroundTint))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(BottomAppBar::getCradleVerticalOffset,
                BottomAppBar::setCradleVerticalOffset,
                max = screenHeight.toFloat()))
        // setElevation is in View
        add(PropertyCategory.COMMON, FabAlignmentMode())
        add(PropertyCategory.COMMON, FabAnimationMode())
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(BottomAppBar::getFabCradleMargin,
                BottomAppBar::setFabCradleMargin))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(BottomAppBar::getFabCradleRoundedCornerRadius,
                BottomAppBar::setFabCradleRoundedCornerRadius))
        add(PropertyCategory.COMMON,
            BooleanProperty(BottomAppBar::getHideOnScroll,
                BottomAppBar::setHideOnScroll))
        add(PropertyCategory.COMMON,
            StringProperty(BottomAppBar::getSubtitle, BottomAppBar::setSubtitle))
        add(PropertyCategory.COMMON,
            StringProperty(BottomAppBar::getTitle, BottomAppBar::setTitle))
    }

    private class FabAlignmentMode : SingleChoiceProperty<BottomAppBar, Int>(BottomAppBar::getFabAlignmentMode, BottomAppBar::setFabAlignmentMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER, "MODE CENTER")
            map.put(BottomAppBar.FAB_ALIGNMENT_MODE_END, "MODE END")
        }
    }

    private class FabAnimationMode : SingleChoiceProperty<BottomAppBar, Int>(BottomAppBar::getFabAnimationMode, BottomAppBar::setFabAnimationMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(BottomAppBar.FAB_ANIMATION_MODE_SCALE, "MODE SCALE")
            map.put(BottomAppBar.FAB_ANIMATION_MODE_SLIDE, "MODE SLIDE")
        }
    }
}