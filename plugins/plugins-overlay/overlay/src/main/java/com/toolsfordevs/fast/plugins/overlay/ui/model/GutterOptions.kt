package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject
import java.util.*


internal class GutterOptions : IFastPrefObject
{
    var id: String = UUID.randomUUID().toString()
    var name: String = "New gutter"
    var enabled: Boolean = true
    var expanded: Boolean = true
    var color: Int = MaterialColor.RED_500
    var strokeWidth: Int = 1
    var strokeStyle: Int = 0
    var offset: Int = 16
    var offsetUnit: Int = Dimens.DP.unit
    var startFrom: Int = 0
    var direction: Int = 0

    val offsetPixel: Int
        get() = Dimens.Unit.from(offsetUnit).toPx(offset)

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("enabled", enabled)
            put("expanded", expanded)
            put("color", color)
            put("strokeWidth", strokeWidth)
            put("strokeStyle", strokeStyle)
            put("offset", offset)
            put("offsetUnit", offsetUnit)
            put("startFrom", startFrom)
            put("direction", direction)
        }
    }

    override fun fromJSON(json: JSONObject)
    {
        id = json.optString("id", UUID.randomUUID().toString())
        name = json.optString("name", "New gutter")
        enabled = json.optBoolean("enabled", true)
        expanded = json.optBoolean("expanded", true)
        color = json.optInt("color", 0)
        strokeWidth = json.optInt("strokeWidth", 0)
        strokeStyle = json.optInt("strokeStyle", 0)
        offset = json.optInt("offset", 0)
        offsetUnit = json.optInt("offsetUnit", 0)
        startFrom = json.optInt("startFrom", 0)
        direction = json.optInt("direction", 0)

    }
}