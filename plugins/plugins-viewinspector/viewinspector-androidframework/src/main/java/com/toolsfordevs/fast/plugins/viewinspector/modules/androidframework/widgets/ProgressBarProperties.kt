package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.os.Build
import android.widget.ProgressBar
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class ProgressBarProperties: ViewPropertyHolder(ProgressBar::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, BooleanProperty(ProgressBar::isIndeterminate, ProgressBar::setIndeterminate))
        add(PropertyCategory.COMMON, DrawableProperty(ProgressBar::getIndeterminateDrawable, ProgressBar::setIndeterminateDrawable))
        add(PropertyCategory.COMMON, DrawableProperty(ProgressBar::getIndeterminateDrawable, ProgressBar::setIndeterminateDrawableTiled))
        add(PropertyCategory.COMMON, ColorStateListProperty(ProgressBar::getIndeterminateTintList, ProgressBar::setIndeterminateTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(ProgressBar::getIndeterminateTintMode, ProgressBar::setIndeterminateTintMode))
        // ( Interpolator )
        add(PropertyCategory.COMMON, IntRangeProperty(ProgressBar::getMax, ProgressBar::setMax, min = -1000, max = 1000))

        // Android Q
        //add(PropertyCategory.COMMON, DimensionRangeProperty("Max Height", VariableUnitValueDelegate(view, android.widget.ProgressBar::getMaxHeight, android.widget.ProgressBar::setMaxHeight, max = screenHeight), "29"))
        //add(PropertyCategory.COMMON, DimensionRangeProperty("Max Width", VariableUnitValueDelegate(view, android.widget.ProgressBar::getMaxWidth, android.widget.ProgressBar::setMaxWidth, max = screenWidth), "29"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            add(PropertyCategory.COMMON, IntRangeProperty(ProgressBar::getMin, ProgressBar::setMin, min = -1000, max = 1000).apply { newApi = "26" })

        // Android Q
        //add(PropertyCategory.COMMON, DimensionRangeProperty("Min Height", VariableUnitValueDelegate(view, android.widget.ProgressBar::getMinHeight, android.widget.ProgressBar::setMinHeight, max = screenHeight), "29"))
        //add(PropertyCategory.COMMON, DimensionRangeProperty("Min Width", VariableUnitValueDelegate(view, android.widget.ProgressBar::getMinWidth, android.widget.ProgressBar::setMinWidth, max = screenWidth), "29"))

        add(PropertyCategory.COMMON, IntRangeProperty(ProgressBar::getProgress, ProgressBar::setProgress, min = -1000, max = 1000))
        add(PropertyCategory.COMMON, ColorStateListProperty(ProgressBar::getProgressBackgroundTintList, ProgressBar::setProgressBackgroundTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(ProgressBar::getProgressBackgroundTintMode, ProgressBar::setProgressBackgroundTintMode))
        add(PropertyCategory.COMMON, DrawableProperty(ProgressBar::getProgressDrawable, ProgressBar::setProgressDrawable))
        add(PropertyCategory.COMMON, DrawableProperty(ProgressBar::getProgressDrawable, ProgressBar::setProgressDrawableTiled))
        add(PropertyCategory.COMMON, ColorStateListProperty(ProgressBar::getProgressTintList, ProgressBar::setProgressTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(ProgressBar::getProgressTintMode, ProgressBar::setProgressTintMode))
        add(PropertyCategory.COMMON, IntRangeProperty(ProgressBar::getSecondaryProgress, ProgressBar::setSecondaryProgress, min = -1000, max = 1000))
        add(PropertyCategory.COMMON, ColorStateListProperty(ProgressBar::getSecondaryProgressTintList, ProgressBar::setSecondaryProgressTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(ProgressBar::getSecondaryProgressTintMode, ProgressBar::setSecondaryProgressTintMode))
    }
}