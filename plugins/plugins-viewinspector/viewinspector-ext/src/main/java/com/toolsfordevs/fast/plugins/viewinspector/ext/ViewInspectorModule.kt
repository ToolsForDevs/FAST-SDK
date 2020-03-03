package com.toolsfordevs.fast.plugins.viewinspector.ext

import com.toolsfordevs.fast.core.FastModule


abstract class ViewInspectorModule(id:String, name:String = "") : FastModule(id, name)
{
    private val holders = arrayListOf<ViewPropertyHolder>()
    private val renderers = arrayListOf<ViewPropertyRenderer<*>>()

    protected fun addWidget(holder: ViewPropertyHolder)
    {
        holders.add(holder)
    }

    protected fun addRenderer(renderer: ViewPropertyRenderer<*>)
    {
        renderers.add(renderer)
    }

    companion object
    {
        fun getHolders():List<ViewPropertyHolder>
        {
            return getModules(ViewInspectorModule::class).flatMap { it.holders }
        }

        fun getRenderers():List<ViewPropertyRenderer<*>>
        {
            return getModules(ViewInspectorModule::class).flatMap { it.renderers }
        }
    }
}