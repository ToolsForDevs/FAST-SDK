package com.toolsfordevs.fast.modules.formatters.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter
import org.json.JSONArray
import org.json.JSONObject


class JsonFormatter : FastFormatter
{
    override val name: String = "JSON"

    override fun canFormatData(data: Any?): Boolean
    {
        if (data is JSONObject || data is JSONArray || data is String)
        {
            if (data is String && !(data.startsWith("{") || data.startsWith("[") && (data.endsWith("{") || data.endsWith("["))))
                return false

            return true
        }

        return false
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        if (!canFormatData(data))
        {
            return "Error, supplied value is not a JsonObject, a JsonArray or a String\n$data"
        }

        if (data is String)
        {
            try
            {
                return JSONObject(data).toString(2)
            }
            catch (e: Exception)
            {
            }

            try
            {
                return JSONArray(data).toString(2)
            }
            catch (e: Exception)
            {
            }

            return "Invalid Json string, couldn't parse it\n$data"
        }


        if (data is JSONObject)
            return data.toString(2)

        return (data as JSONArray).toString(2)
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