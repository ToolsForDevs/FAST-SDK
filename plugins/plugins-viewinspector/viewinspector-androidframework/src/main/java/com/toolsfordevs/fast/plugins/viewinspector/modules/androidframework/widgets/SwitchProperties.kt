package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.Switch
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class SwitchProperties: ViewPropertyHolder(Switch::class.java)
{
    init
    {
        // checked is in CompoundButton
        add(PropertyCategory.COMMON, BooleanProperty(Switch::getShowText, Switch::setShowText))
        add(PropertyCategory.COMMON, BooleanProperty(Switch::getSplitTrack, Switch::setSplitTrack))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Switch::getSwitchMinWidth, Switch::setSwitchMinWidth, max = screenWidth))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Switch::getSwitchPadding, Switch::setSwitchPadding, max = dp(192)))
        // setSwitchTextAppearance // ToDo
        // setSwitchTypeface
        add(PropertyCategory.COMMON, StringProperty(Switch::getTextOff, Switch::setTextOff))
        add(PropertyCategory.COMMON, StringProperty(Switch::getTextOn, Switch::setTextOn))

        add(PropertyCategory.COMMON, DrawableProperty(Switch::getThumbDrawable, Switch::setThumbDrawable))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Switch::getThumbTextPadding, Switch::setThumbTextPadding, max = dp(192)))

        if (AndroidVersion.isMinMarshmallow())
        {
            add(PropertyCategory.COMMON, ColorStateListProperty(Switch::getThumbTintList, Switch::setThumbTintList))
            add(PropertyCategory.COMMON, ColorTintModeProperty(Switch::getThumbTintMode, Switch::setThumbTintMode))
        }

        add(PropertyCategory.COMMON, ColorDrawableProperty(Switch::getTrackDrawable, Switch::setTrackDrawable))

        if (AndroidVersion.isMinMarshmallow())
        {
            add(PropertyCategory.COMMON, ColorStateListProperty(Switch::getTrackTintList, Switch::setTrackTintList))
            add(PropertyCategory.COMMON, ColorTintModeProperty(Switch::getTrackTintMode, Switch::setTrackTintMode))
        }

    }
}