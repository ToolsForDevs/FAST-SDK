package com.toolsfordevs.fast.modules.formatters.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DateFormatter : FastFormatter
{
    override val name: String = "Date"

    override fun canFormatData(data: Any?): Boolean
    {
        if (data is Date || data is Calendar || data is Long || data is Int)
            return true

        return false
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        val df = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)

        if (!canFormatData(data))
        {
            return "Error, supplied value is not a Date, a Calendar, an Int or a Long\n$data"
        }

        if (data is Int)
            return df.format(Date(data.toLong()))
        if (data is Long)
            return df.format(Date(data))

        if (data is Date)
            return df.format(data)

        return df.format(Date((data as Calendar).timeInMillis))

    }
}