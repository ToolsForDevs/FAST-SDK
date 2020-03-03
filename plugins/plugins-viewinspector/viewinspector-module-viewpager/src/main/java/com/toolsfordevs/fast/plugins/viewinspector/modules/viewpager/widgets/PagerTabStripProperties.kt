package com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets

import androidx.viewpager.widget.PagerTabStrip
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.ColorProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class PagerTabStripProperties: ViewPropertyHolder(PagerTabStrip::class.java)
{
    init
    {
        // setBackgroundDrawable is in View
        add(PropertyCategory.COMMON,
            BooleanProperty(PagerTabStrip::getDrawFullUnderline,
                PagerTabStrip::setDrawFullUnderline))
        add(PropertyCategory.COMMON,
            ColorProperty(PagerTabStrip::getTabIndicatorColor,
                PagerTabStrip::setTabIndicatorColor))
        // setTextSpacing has no getter is in PagerTitleStrip
    }
}