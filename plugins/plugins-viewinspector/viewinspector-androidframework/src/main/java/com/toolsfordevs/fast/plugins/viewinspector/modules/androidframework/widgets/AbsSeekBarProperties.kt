package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.os.Build
import android.widget.AbsSeekBar
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class AbsSeekBarProperties: ViewPropertyHolder(AbsSeekBar::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, IntRangeProperty(AbsSeekBar::getKeyProgressIncrement, AbsSeekBar::setKeyProgressIncrement, min = 1, max = 100))

        // Max is in ProgressBar
        // Min is in ProgressBar

        add(PropertyCategory.COMMON, BooleanProperty(AbsSeekBar::getSplitTrack, AbsSeekBar::setSplitTrack))

        add(PropertyCategory.COMMON, DrawableProperty(AbsSeekBar::getThumb, AbsSeekBar::setThumb))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(AbsSeekBar::getThumbOffset, AbsSeekBar::setThumbOffset, max = dp(100)))

        add(PropertyCategory.COMMON, ColorStateListProperty(AbsSeekBar::getThumbTintList, AbsSeekBar::setThumbTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(AbsSeekBar::getThumbTintMode, AbsSeekBar::setThumbTintMode))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            add(PropertyCategory.COMMON, DrawableProperty(AbsSeekBar::getTickMark, AbsSeekBar::setTickMark).apply { newApi = "24" })
            add(PropertyCategory.COMMON, ColorStateListProperty(AbsSeekBar::getTickMarkTintList, AbsSeekBar::setTickMarkTintList).apply { newApi = "24" })
            add(PropertyCategory.COMMON, ColorTintModeProperty(AbsSeekBar::getTickMarkTintMode, AbsSeekBar::setTickMarkTintMode).apply { newApi = "24" })
        }
    }
}