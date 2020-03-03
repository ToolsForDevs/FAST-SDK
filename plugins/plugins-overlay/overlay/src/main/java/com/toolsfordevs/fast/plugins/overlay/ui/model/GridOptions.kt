package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import org.json.JSONObject
import java.util.*


internal class GridOptions : IOptions, IFastPrefObject
{
    var id: String = UUID.randomUUID().toString()
    override var name: String = "New grid"
    override var enabled: Boolean = true
    override var expanded: Boolean = true
    var color: Int = MaterialColor.LIGHTBLUE_500
    var strokeWidth: Int = 1
    var strokeStyle: Int = StrokeStyle.SOLID
    var cellWidth: Int = 8
    var cellHeight: Int = 8
    var cellWidthDimension: Int = Dimens.DP.unit
    var cellHeightDimension: Int = Dimens.DP.unit
    var vOffset: Int = 0
    var hOffset: Int = 0
    var vOffsetDimension: Int = Dimens.DP.unit
    var hOffsetDimension: Int = Dimens.DP.unit
    var vStartFrom: Int = VStartFrom.START
    var hStartFrom: Int = HStartFrom.TOP

    val cellWidthPixel: Int
        get() = Dimens.Unit.from(cellWidthDimension).toPx(cellWidth)

    val cellHeightPixel: Int
        get() = Dimens.Unit.from(cellHeightDimension).toPx(cellHeight)

    val vOffsetPixel: Int
        get() = Dimens.Unit.from(vOffsetDimension).toPx(vOffset)

    val hOffsetPixel: Int
        get() = Dimens.Unit.from(hOffsetDimension).toPx(hOffset)

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
            put("cellWidth", cellWidth)
            put("cellHeight", cellHeight)
            put("cellWidthDimension", cellWidthDimension)
            put("cellHeightDimension", cellHeightDimension)
            put("vOffset", vOffset)
            put("hOffset", hOffset)
            put("vOffsetDimension", vOffsetDimension)
            put("hOffsetDimension", hOffsetDimension)
            put("vStartFrom", vStartFrom)
            put("hStartFrom", hStartFrom)
        }
    }

    override fun fromJSON(json: JSONObject)
    {
        id = json.optString("id", UUID.randomUUID().toString())
        name = json.optString("name", "New grid")
        enabled = json.optBoolean("enabled", true)
        expanded = json.optBoolean("expanded", true)
        color = json.optInt("color", 0)
        strokeWidth = json.optInt("strokeWidth", 0)
        strokeStyle = json.optInt("strokeStyle", 0)
        cellWidth = json.optInt("cellWidth", 0)
        cellHeight = json.optInt("cellHeight", 0)
        cellWidthDimension = json.optInt("cellWidthDimension", 0)
        cellHeightDimension = json.optInt("cellHeightDimension", 0)
        vOffset = json.optInt("vOffset", 0)
        hOffset = json.optInt("hOffset", 0)
        vOffsetDimension = json.optInt("vOffsetDimension", 0)
        hOffsetDimension = json.optInt("hOffsetDimension", 0)
        vStartFrom = json.optInt("vStartFrom", 0)
        hStartFrom = json.optInt("hStartFrom", 0)

    }
}