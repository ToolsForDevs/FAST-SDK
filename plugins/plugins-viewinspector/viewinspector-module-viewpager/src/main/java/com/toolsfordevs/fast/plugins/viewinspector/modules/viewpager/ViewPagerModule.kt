package com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets.PagerTabStripProperties
import com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets.PagerTitleStripProperties
import com.toolsfordevs.fast.plugins.viewinspector.modules.viewpager.widgets.ViewPagerProperties

@FastIncludeModule
class ViewPagerModule : ViewInspectorModule("ViewPager")
{
    override fun initialize()
    {
        addWidget(ViewPagerProperties()) // super =
        addWidget(PagerTitleStripProperties()) // super = ViewGroup
        addWidget(PagerTabStripProperties()) // super = ViewGroup
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}