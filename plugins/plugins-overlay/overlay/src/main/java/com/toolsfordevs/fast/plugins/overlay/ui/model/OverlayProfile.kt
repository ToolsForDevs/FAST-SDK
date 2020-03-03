package com.toolsfordevs.fast.plugins.overlay.ui.model

import com.toolsfordevs.fast.core.prefs.IFastPrefObject
import org.json.JSONObject
import java.util.*


internal class OverlayProfile : IFastPrefObject
{
    companion object
    {
        const val DEFAULT_ID = "default"

        fun default(): OverlayProfile
        {
            return OverlayProfile()
                .apply { id = DEFAULT_ID; name = "Default" }
        }
    }

    var id: String = UUID.randomUUID().toString()
        private set

    var name: String = "New profile"
    val hierarchyOptions = HierarchyBoundOptions()
    val boundsOptions = ViewBoundOptions()
    val marginOptions = MarginOptions()
    val marginDimensionsOptions = MarginDimensionsOptions()
    val paddingOptions = PaddingOptions()
    val paddingDimensionsOptions = PaddingDimensionsOptions()
    val rulerOptions = RulerOptions()
    val sizeOptions = ViewSizeOptions()
    val positionOptions = ViewPositionOptions()

    val grids = arrayListOf<GridOptions>()
    val gutters = arrayListOf<GutterOptions>()

    override fun fromJSON(json: JSONObject)
    {
        id = json.getString("id")
        name = json.getString("name")
        hierarchyOptions.fromJSON(json.getJSONObject("hierarchyOptions"))
        boundsOptions.fromJSON(json.getJSONObject("boundsOptions"))
        paddingOptions.fromJSON(json.getJSONObject("marginOptions"))
        marginDimensionsOptions.fromJSON(json.getJSONObject("marginDimensionsOptions"))
        paddingOptions.fromJSON(json.getJSONObject("paddingOptions"))
        paddingDimensionsOptions.fromJSON(json.getJSONObject("paddingDimensionsOptions"))
        rulerOptions.fromJSON(json.getJSONObject("rulerOptions"))
        sizeOptions.fromJSON(json.getJSONObject("sizeOptions"))
        positionOptions.fromJSON(json.getJSONObject("positionOptions"))

        grids.addAll(fromJsonArray(json.getJSONArray("grids")).map { GridOptions().apply { fromJSON(it) } })
        gutters.addAll(fromJsonArray(json.getJSONArray("gutters")).map { GutterOptions()
            .apply { fromJSON(it) } })
    }

    override fun toJSON(): JSONObject
    {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("hierarchyOptions", hierarchyOptions.toJSON())
            put("boundsOptions", boundsOptions.toJSON())
            put("marginOptions", marginOptions.toJSON())
            put("marginDimensionsOptions", marginDimensionsOptions.toJSON())
            put("paddingOptions", paddingOptions.toJSON())
            put("paddingDimensionsOptions", paddingDimensionsOptions.toJSON())
            put("rulerOptions", rulerOptions.toJSON())
            put("sizeOptions", sizeOptions.toJSON())
            put("positionOptions", positionOptions.toJSON())

            put("grids", toJsonArray(grids))
            put("gutters", toJsonArray(gutters))
        }
    }
}