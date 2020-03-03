package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.SwitchCompat
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class SwitchCompatProperties: ViewPropertyHolder(SwitchCompat::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            StringProperty(SwitchCompat::getTextOn, SwitchCompat::setTextOn))
        add(PropertyCategory.COMMON,
            StringProperty(SwitchCompat::getTextOff, SwitchCompat::setTextOff))
        add(PropertyCategory.COMMON,
            BooleanProperty(SwitchCompat::getShowText, SwitchCompat::setShowText))
        add(PropertyCategory.COMMON,
            BooleanProperty(SwitchCompat::getSplitTrack, SwitchCompat::setSplitTrack))

        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(SwitchCompat::getSwitchPadding,
                SwitchCompat::setSwitchPadding,
                max = dp(192)))

        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(SwitchCompat::getThumbTextPadding,
                SwitchCompat::setThumbTextPadding,
                max = dp(192)))
        add(PropertyCategory.COMMON,
            DrawableProperty(SwitchCompat::getThumbDrawable,
                SwitchCompat::setThumbDrawable))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(SwitchCompat::getThumbTintList,
                SwitchCompat::setThumbTintList))
        add(PropertyCategory.COMMON,
            ColorTintModeProperty(SwitchCompat::getThumbTintMode,
                SwitchCompat::setThumbTintMode))

        add(PropertyCategory.COMMON,
            DrawableProperty(SwitchCompat::getTrackDrawable,
                SwitchCompat::setTrackDrawable))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(SwitchCompat::getTrackTintList,
                SwitchCompat::setTrackTintList))
        add(PropertyCategory.COMMON,
            ColorTintModeProperty(SwitchCompat::getTrackTintMode,
                SwitchCompat::setTrackTintMode))

        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(SwitchCompat::getSwitchMinWidth,
                SwitchCompat::setSwitchMinWidth,
                max = screenWidth))
    }
}