package com.toolsfordevs.fast.plugins.viewinspector.modules.drawerlayout

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.drawerlayout.widgets.DrawerLayoutProperties

@FastIncludeModule
class DrawerLayoutModule : ViewInspectorModule("DrawerLayout")
{
    override fun initialize()
    {
        addWidget(DrawerLayoutProperties())
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}