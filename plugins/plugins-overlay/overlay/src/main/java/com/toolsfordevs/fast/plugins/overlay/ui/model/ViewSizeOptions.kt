package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class ViewSizeOptions : IOptions, IFastPrefObject
{
    override val name: String = "Size"
    override var enabled = true
    override var expanded = true
    var backgroundColor = MaterialColor.PINK_500
    var borderColor = MaterialColor.PINK_500
    val text = "S"
    var textColor = MaterialColor.WHITE_100
    var unit = Dimens.DP.unit
    var showUnit = false
    var roundedCorners = true

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

    }
}