package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class PaddingOptions : IOptions, IFastPrefObject
{
    override val name: String = "Padding"
    override var enabled = false
    override var expanded = true
    var backgroundColor = ColorUtil.setAlpha(MaterialColor.BLUE_500, 0.35f)
    var borderColor = MaterialColor.BLUE_500
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
        enabled = json.optBoolean("enabled", true)
        expanded = json.optBoolean("expanded", true)
        backgroundColor = json.optInt("backgroundColor", 0)
        borderColor = json.optInt("borderColor", 0)
        backgroundStyle = json.optInt("backgroundStyle", 0)
        strokeStyle = json.optInt("strokeStyle", 0)

    }

}