package com.toolsfordevs.fast.plugins.viewinspector.modules.coordinatorlayout

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.coordinatorlayout.widgets.CoordinatorLayoutProperties

@FastIncludeModule
class CoordinatorLayoutModule : ViewInspectorModule("CoordinatorLayout")
{
    override fun initialize()
    {
        addWidget(CoordinatorLayoutProperties())
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}