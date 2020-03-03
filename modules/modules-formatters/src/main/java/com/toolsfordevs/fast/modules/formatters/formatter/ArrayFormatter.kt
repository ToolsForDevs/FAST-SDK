package com.toolsfordevs.fast.modules.formatters.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter


class ArrayFormatter : FastFormatter
{
    override val name: String = "Array"

    override fun canFormatData(data: Any?): Boolean
    {
        if (data is IntArray
                || data is LongArray
                || data is BooleanArray
                || data is FloatArray
                || data is DoubleArray
                || data is ShortArray
                || data is Array<*>)
            return true

        return false
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        if (!canFormatData(data))
            return "Error, supplied value is not an Iterable\n$data"

        val iterator:Iterator<*>
        val size:Int

        if (data is IntArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else if (data is LongArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else if (data is BooleanArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else if (data is FloatArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else if (data is DoubleArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else if (data is ShortArray)
        {
            iterator = data.iterator()
            size = data.size
        }
        else// if (data is Array<*>)
        {
            iterator = (data as Array<*>).iterator()
            size = data.size
        }

        var s = data::class.java.simpleName

        val indexCharSize = "$size".length

        for (withIndex in iterator.withIndex())
        {
            s += "\n${formatIndex(withIndex.index, indexCharSize)} -> ${withIndex.value}"

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