package com.toolsfordevs.fast.plugins.networkexplorer.formatter

import org.json.JSONArray
import org.json.JSONObject

class JsonFormatter
{
    fun format(json: String): String
    {
        try
        {
            return JSONObject(json).toString(2)
        }
        catch (e: Exception)
        {

        }

        return try
        {
            JSONArray(json).toString(2)
        }
        catch (e: Exception)
        {
            json
        }
    }
}