package com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets

import androidx.viewpager.widget.PagerTitleStrip
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class PagerTitleStripProperties: ViewPropertyHolder(PagerTitleStrip::class.java)
{
    init
    {
        // setGravity has no getter
        // setNonPrimaryAlpha has no getter
        // setTextColor has no getter
        // setTextSize has no getter
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(PagerTitleStrip::getTextSpacing,
                PagerTitleStrip::setTextSpacing,
                max = dp(100)))
    }
}