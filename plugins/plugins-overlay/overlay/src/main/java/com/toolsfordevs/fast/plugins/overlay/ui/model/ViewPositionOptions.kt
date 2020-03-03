package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class ViewPositionOptions : IOptions, IFastPrefObject
{
    override val name: String = "Position"
    override var enabled = false
    override var expanded = true
    var backgroundColor = MaterialColor.BLUEGREY_500
    var borderColor = MaterialColor.BLUEGREY_500
    val text = "P"
    var textColor = MaterialColor.WHITE_100
    var unit = Dimens.DP.unit
    var showUnit = false
    var roundedCorners = false
    var relativeTo = RelativePosition.PARENT

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
            put("relativeTo", relativeTo)
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
        relativeTo = json.getInt("relativeTo")
    }
}