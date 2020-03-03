package com.toolsfordevs.fast.plugins.viewinspector.modules.recyclerview

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.recyclerview.widgets.RecyclerViewProperties

@FastIncludeModule
class RecyclerViewModule : ViewInspectorModule("RecyclerView")
{
    override fun initialize()
    {
        addWidget(RecyclerViewProperties())
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}