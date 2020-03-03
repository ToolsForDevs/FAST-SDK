package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class MarginOptions : IOptions, IFastPrefObject
{
    override val name: String = "Margins"
    override var enabled = false
    override var expanded = true
    var backgroundColor = ColorUtil.setAlpha(MaterialColor.LIME_500, 0.35f)
    var borderColor = MaterialColor.LIME_500
    var backgroundStyle = BackgroundStyle.STRIPS
    var strokeStyle = StrokeStyle.SOLID

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("enabled", enabled)
            put("expanded", expanded)
            put("backgroundColor", backgroundColor)
            put("borderColor", borderColor)
            put("backgroundStyle", backgroundStyle)
            put("strokeStyle", strokeStyle)
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

    }
}