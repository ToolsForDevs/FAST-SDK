package com.toolsfordevs.fast.modules.formatters

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.formatters.formatter.*
import kotlin.collections.ArrayList

@Keep
object FastFormatterStore
{
    private var isInitialized = false

    private val formatters:ArrayList<FastFormatter> = arrayListOf()

    fun addFormatter(formatter: FastFormatter)
    {
        formatters.add(formatter)
    }

    fun getFormatters():List<FastFormatter>
    {
        if (!isInitialized)
        {
            formatters.add(0, JsonFormatter())
            formatters.add(0, DateFormatter())
            formatters.add(0, MapFormatter())
            formatters.add(0, ArrayFormatter())
            formatters.add(0, IterableFormatter())
            formatters.addAll(0, FastFormatterModule.getFormatters())
            isInitialized = true
        }

        return formatters.toList()
    }
}