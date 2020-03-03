package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.ColorProperty
import com.toolsfordevs.fast.plugins.viewinspector.R


internal class ColorRenderer(parent: ViewGroup) : ViewPropertyRenderer<ColorProperty<*>>(parent,
    R.layout.fast_renderer_item_color) {
    private val hexCode = itemView.findViewById<TextView>(R.id.color_hexcode)
    private val colorView = itemView.findViewById<View>(R.id.color_view)
    private val clearButton = itemView.findViewById<ImageButton>(R.id.clear_button)

    override fun bind(data: ColorProperty<*>)
    {
        refresh(data)

        clearButton.setOnClickListener {
            data.setValue(0x00000000)
            noColor()
        }

        itemView.setOnClickListener {
            ColorPickerDialog(itemView.context, { colorResource ->
                data.setValue(colorResource.value ?: ResUtils.getColor(colorResource.resId))
                refresh(data)
            }).show()
        }
    }

    private fun refresh(data: ColorProperty<*>) {
//        val hex = "#" + color.toString(16).toUpperCase()
        if (data.hasValue()) {
            val color = data.getValue()!!
            val hex = String.format("#%08X", 0xFFFFFFFF.toInt() and color)
            hexCode.text = hex
            colorView.setBackgroundColor(color)
            clearButton.show()
        } else {
            noColor()
        }
    }

    private fun noColor()
    {
        hexCode.text = "NONE"
        colorView.setBackgroundResource(0)
        clearButton.hide()
    }
}