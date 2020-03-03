package com.toolsfordevs.fast.modules.formatters

import com.toolsfordevs.fast.core.FastModule

/**
 * Logger module to add custom formatter for various data types
 */
abstract class FastFormatterModule(id: String, name: String) : FastModule(id, name)
{
    private val formatters = arrayListOf<FastFormatter>()

    protected fun addFormatter(holder: FastFormatter)
    {
        formatters.add(holder)
    }

    companion object
    {
        internal fun getFormatters():List<FastFormatter>
        {
            return getModules(FastFormatterModule::class).flatMap { it.formatters }
        }
    }
}