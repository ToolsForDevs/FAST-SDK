package com.toolsfordevs.fast.plugins.logger.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter


internal open class StringFormatter : FastFormatter
{
    override val name: String = "String (default)"

    override fun canFormatData(data: Any?): Boolean
    {
        return true // This formatter can format anything
    }

    override fun formatToString(data: Any?): String
    {
        return data?.toString() ?: "null"
    }
}