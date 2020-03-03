package com.toolsfordevs.fast.plugins.viewinspector.modules.cardview

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.cardview.widgets.CardViewProperties

@FastIncludeModule
class CardViewModule : ViewInspectorModule("CardView")
{
    override fun initialize()
    {
        addWidget(CardViewProperties())
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}