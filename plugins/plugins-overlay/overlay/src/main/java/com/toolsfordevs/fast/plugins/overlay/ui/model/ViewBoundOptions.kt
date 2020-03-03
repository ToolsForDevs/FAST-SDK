package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject


internal class ViewBoundOptions : IOptions, IFastPrefObject
{
    override val name: String = "Bounds"
    override var enabled = true
    override var expanded = true
    var backgroundColor = ColorUtil.setAlpha(MaterialColor.RED_500, 0.5f)
    var borderColor = MaterialColor.RED_500
    var backgroundStyle = BackgroundStyle.NONE
    var strokeStyle = StrokeStyle.SOLID
    var showCorners = true // ToDo : shape option : inside triangle, circle, square
    // ToDo Show a small circle on each edge's center
    // option : show/hide, maybe shape (circle/square), maybe allow to choose color
    /*
        _______._______
        |
        .
        |
        _______._______

        useful to see centerX and centerX of each side of the view
     */

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

    }
}