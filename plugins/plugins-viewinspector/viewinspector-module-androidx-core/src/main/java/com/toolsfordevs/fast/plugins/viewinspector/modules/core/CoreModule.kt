package com.toolsfordevs.fast.plugins.viewinspector.modules.core

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.core.widgets.NestedScrollViewProperties

@FastIncludeModule
class CoreModule : ViewInspectorModule("Core")
{
    override fun initialize()
    {
        addWidget(NestedScrollViewProperties())
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}