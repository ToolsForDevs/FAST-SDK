package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class HierarchyBoundOptions : IOptions, IFastPrefObject
{
    override val name: String = "Hierarchy Bounds"
    override var enabled = true
    override var expanded = true
    var backgroundColor = 0x77CCCCCC.toInt()
    var borderColor = 0xFFCCCCCC.toInt()
    var backgroundStyle = BackgroundStyle.NONE
    var strokeStyle = StrokeStyle.SOLID
    var showCorners = true
    var excludeViewGroups = true
    var scrim = true
    var scrimColor = MaterialColor.BLACK_38

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("enabled", enabled)
            put("expanded", expanded)
            put("backgroundColor", backgroundColor)
            put("borderColor", borderColor)
            put("backgroundStyle", backgroundStyle)
            put("strokeStyle", strokeStyle)
            put("showCorners", showCorners)
            put("excludeViewGroups", excludeViewGroups)
            put("scrim", scrim)
            put("scrimColor", scrimColor)
        }
    }

    override fun fromJSON(json: JSONObject)
    {
        enabled = json.getBoolean("enabled")
        expanded = json.getBoolean("expanded")
        backgroundColor = json.getInt("backgroundColor")
        borderColor = json.getInt("borderColor")
        backgroundStyle = json.getInt("backgroundStyle")
        strokeStyle = json.getInt("strokeStyle")
        showCorners = json.getBoolean("showCorners")
        excludeViewGroups = json.getBoolean("excludeViewGroups")
        scrim = json.getBoolean("scrim")
        scrimColor = json.getInt("scrimColor")

    }
}