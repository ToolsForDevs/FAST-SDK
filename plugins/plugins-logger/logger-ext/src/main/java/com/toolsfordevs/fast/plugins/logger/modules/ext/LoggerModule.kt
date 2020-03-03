package com.toolsfordevs.fast.plugins.logger.modules.ext

import android.view.ViewGroup
import com.toolsfordevs.fast.core.FastModule
import com.toolsfordevs.fast.modules.formatters.FastFormatter
import kotlin.reflect.KClass

abstract class LoggerModule(id: String, name: String = "") : FastModule(id, name)
{
    private val renderers = arrayListOf<Pair<KClass<*>, (ViewGroup) -> LogRenderer<*>>>()

    inline fun <reified T : FastFormatter> addRenderer(noinline factory: (ViewGroup) -> LogRenderer<T>)
    {
        addRenderer(T::class, factory)
    }

    fun addRenderer(type: KClass<*>, factory: (ViewGroup) -> LogRenderer<*>)
    {
        renderers.add(Pair(type, factory))
    }

    companion object
    {
        fun getRenderers():List<Pair<KClass<*>, (ViewGroup) -> LogRenderer<*>>>
        {
            return getModules(LoggerModule::class).flatMap { it.renderers }
        }
    }
}