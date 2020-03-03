package com.toolsfordevs.fast.modules.formatters.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter


class MapFormatter : FastFormatter
{
    override val name: String = "Map"

    override fun canFormatData(data: Any?): Boolean
    {
        if (data is Map<*, *>)
            return true

        return false
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        if (!canFormatData(data))
            return "Error, supplied value is not a Map\n$data"

        val map = data as Map<*, *>

        var s = map::class.java.name

        for (entry in map.entries)
        {
            s += "\n${entry.key} -> ${entry.value}"
        }

        return s
    }
}