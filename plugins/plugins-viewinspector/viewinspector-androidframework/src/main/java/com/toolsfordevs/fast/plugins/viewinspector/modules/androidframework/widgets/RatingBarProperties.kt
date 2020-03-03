package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.RatingBar
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.FloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.IntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class RatingBarProperties : ViewPropertyHolder(RatingBar::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, BooleanProperty(RatingBar::isIndicator, RatingBar::setIsIndicator))
        // setMax is in ProgressBar
        add(PropertyCategory.COMMON, IntRangeProperty(RatingBar::getNumStars, RatingBar::setNumStars, max = 15))
        add(PropertyCategory.COMMON, FloatRangeProperty(RatingBar::getRating, RatingBar::setRating, max = 15f))
        add(PropertyCategory.COMMON, FloatRangeProperty(RatingBar::getStepSize, RatingBar::setStepSize, max = 5f))
    }
}