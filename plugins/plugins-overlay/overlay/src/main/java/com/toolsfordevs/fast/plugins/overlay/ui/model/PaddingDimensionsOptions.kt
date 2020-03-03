package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class PaddingDimensionsOptions : IOptions, IFastPrefObject
{
    override val name: String = "Padding dimensions"
    override var enabled = true
    override var expanded = true
    var backgroundColor = MaterialColor.PINK_500
    var borderColor = MaterialColor.PINK_500
    val text = "M"
    var textColor = MaterialColor.WHITE_100
    var unit = Dimens.DP.unit
    var showUnit = false
    var roundedCorners = true
    var strokeColor = MaterialColor.PINK_500
    var strokeStyle = StrokeStyle.SOLID

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("enabled", enabled)
            put("expanded", expanded)
            put("backgroundColor", backgroundColor)
            put("borderColor", borderColor)
            put("textColor", textColor)
            put("unit", unit)
            put("showUnit", showUnit)
            put("roundedCorners", roundedCorners)
            put("strokeColor", strokeColor)
            put("strokeStyle", strokeStyle)
        }
    }

    override fun fromJSON(json: JSONObject)
    {
        enabled = json.getBoolean("enabled")
        expanded = json.getBoolean("expanded")
        backgroundColor = json.getInt("backgroundColor")
        borderColor = json.getInt("borderColor")
        textColor = json.getInt("textColor")
        unit = json.getInt("unit")
        showUnit = json.getBoolean("showUnit")
        roundedCorners = json.getBoolean("roundedCorners")
        strokeColor = json.getInt("strokeColor")
        strokeStyle = json.getInt("strokeStyle")
    }
}