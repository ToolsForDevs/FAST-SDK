package com.toolsfordevs.fast.plugins.logger.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ext.vLinearLayout
import com.toolsfordevs.fast.plugins.logger.modules.ext.LogRenderer
import com.toolsfordevs.fast.plugins.logger.formatter.IntrospectionFormatter


internal class InspectionRenderer(parent:ViewGroup) : LogRenderer<IntrospectionFormatter>(vLinearLayout(parent.context))
{
    init
    {
        itemView.layoutParams = layoutParamsMM()
    }
    
    override fun bind(data: Any?, formatter: IntrospectionFormatter)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}