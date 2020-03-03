package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.MarginDimensionsOptions


internal class MarginDimensionsOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<MarginDimensionsOptions, MarginDimensionsOptionsView>(
    MarginDimensionsOptionsView(parent.context))
{
    override fun bind(data: MarginDimensionsOptions, position: Int)
    {
        super.bind(data, position)

        view.colorPreview.text = data.text
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, null, data::textColor)

        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindColor(data::textColor, view.textColor)
        bindUnit(data::unit, view.unit)
        bindBoolean(data::showUnit, view.showUnit)
        bindBoolean(data::roundedCorners, view.roundedCorners)
        bindColor(data::strokeColor, view.strokeColor)
        bindStrokeStyle(data::strokeStyle, view.strokeStyle)
    }
}

internal class MarginDimensionsOptionsView(context: Context) : BackgroundOptionView(context)
{
    val textColor = makeColorButton()
    val unit = makeSpinner()
    val showUnit = makeSwitch()
    val roundedCorners = makeSwitch()
    val strokeColor = makeColorButton()
    val strokeStyle = makeSpinner()

    init {
        addItem("Text color", textColor)
        addItem("Unit", unit)
        addItem("Show unit", showUnit)
        addItem("Rounded corners", roundedCorners)
        addItem("Stroke color", strokeColor)
        addItem("Stroke style", strokeStyle)
    }
}