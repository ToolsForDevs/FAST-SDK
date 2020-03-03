package com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets

import androidx.viewpager.widget.ViewPager
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.IntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class ViewPagerProperties: ViewPropertyHolder(ViewPager::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, CurrentItemProperty())
        add(PropertyCategory.COMMON,
            IntRangeProperty(ViewPager::getOffscreenPageLimit,
                ViewPager::setOffscreenPageLimit,
                max = 10))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(ViewPager::getPageMargin,
                ViewPager::setPageMargin,
                max = screenWidth))
        // add(PropertyCategory.COMMON, DrawableProperty(ViewPager::getPageMarginDrawable, ViewPager::setPageMarginDrawable)) no getter :(
    }

    private class CurrentItemProperty : IntRangeProperty<ViewPager>(ViewPager::getCurrentItem, ViewPager::setCurrentItem)
    {
        override fun getMaximum(): Int
        {
            if (view.adapter != null)
                return view.adapter!!.count - 1

            return 0
        }
    }
}