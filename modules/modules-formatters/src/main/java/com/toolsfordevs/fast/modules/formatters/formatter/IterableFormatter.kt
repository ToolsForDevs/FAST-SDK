package com.toolsfordevs.fast.modules.formatters.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter


class IterableFormatter : FastFormatter
{
    override val name: String = "Collection/Iterable"

    override fun canFormatData(data: Any?): Boolean
    {
        if (data is Iterable<*>)
            return true

        return false
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        if (!canFormatData(data))
            return "Error, supplied value is not an Iterable\n$data"

        val iterable = data as Iterable<*>

        var s = iterable::class.java.name

        if (iterable is Collection)
        {
            val size = iterable.size - 1
            val indexCharSize = "$size".length

            iterable.forEachIndexed { index, item ->
                s += "\n${formatIndex(index, indexCharSize)} -> $item"
            }
        }
        else
        {
            iterable.forEachIndexed { index, item ->
                s += "\n$index -> $item"
            }
        }

        return s
    }

    private fun formatIndex(index: Int, size: Int): String
    {
        var indexStr = "$index"

        val spacesToAdd = size - indexStr.length

        for (i in 0 until spacesToAdd)
            indexStr += " "

        return indexStr
    }
}