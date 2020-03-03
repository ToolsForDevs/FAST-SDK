package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.ColorStateListProperty
import com.toolsfordevs.fast.plugins.viewinspector.R


internal class ColorStateListRenderer(parent: ViewGroup) : ViewPropertyRenderer<ColorStateListProperty<*>>(parent,
                                                                                                  R.layout.fast_renderer_item_color)
{
    private val hexCode = itemView.findViewById<TextView>(R.id.color_hexcode)
    private val colorView = itemView.findViewById<View>(R.id.color_view)
    private val clearButton = itemView.findViewById<ImageButton>(R.id.clear_button)

    override fun bind(data: ColorStateListProperty<*>)
    {
        refresh(data)

        clearButton.setOnClickListener {
            // If can't set null colorStateList (some widgets don't allow it)
            // then set transparent color
            if (!data.setValue(null))
                data.setValue(ColorStateList.valueOf(Color.TRANSPARENT))

            noColor(data)
        }

        itemView.setOnClickListener {
            ColorPickerDialog(itemView.context, { colorResource ->
                data.setValue(colorResource.colorStateList ?: ColorStateList.valueOf(colorResource.value ?: try
                {
                    ResUtils.getColor(colorResource.resId)
                }
                catch (e: Exception)
                {
                    0xFF000000.toInt()
                }))
                refresh(data)
            }).show()
        }
    }

    private fun refresh(data: ColorStateListProperty<*>)
    {
//        val hex = "#" + color.toString(16).toUpperCase()
        if (data.hasValue())
        {
            val color = data.getValue()!!.defaultColor
            val hex = String.format("#%08X", 0xFFFFFFFF.toInt() and color)
            hexCode.text = hex
            colorView.setBackgroundColor(color)
            clearButton.show()
        }
        else
        {
            noColor(data)
        }
    }

    private fun noColor(data: ColorStateListProperty<*>)
    {
        // To differentiate null from transparent

        var type = "NONE (NULL)"

        if (data.hasValue())
            type = "NONE (TRANSPARENT)"

        hexCode.text = type
        colorView.setBackgroundResource(0)
        clearButton.hide()
    }
}