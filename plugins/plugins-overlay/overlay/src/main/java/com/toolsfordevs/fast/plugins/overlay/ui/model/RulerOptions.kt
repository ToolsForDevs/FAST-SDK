package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class RulerOptions : IOptions, IFastPrefObject
{
    override val name: String = "Ruler"
    override var enabled = true
    override var expanded = true
    var color = MaterialColor.RED_500
    var strokeStyle = StrokeStyle.DASHED

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("enabled", enabled)
            put("expanded", expanded)
            put("color", color)
            put("strokeStyle", strokeStyle)
        }
    }

    override fun fromJSON(json: JSONObject)
    {
        enabled = json.getBoolean("enabled")
        expanded = json.getBoolean("expanded")
        color = json.getInt("color")
        strokeStyle = json.getInt("strokeStyle")

    }
}