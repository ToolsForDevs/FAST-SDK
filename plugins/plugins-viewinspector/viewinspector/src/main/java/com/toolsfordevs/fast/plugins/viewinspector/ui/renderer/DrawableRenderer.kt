package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.drawable.DrawablePickerDialog
import com.toolsfordevs.fast.modules.resources.DrawableResource
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.R

internal class DrawableRenderer(parent: ViewGroup) : ViewPropertyRenderer<DrawableProperty<*>>(parent,
    R.layout.fast_renderer_item_drawable)
{
    private val textview = itemView.findViewById<TextView>(R.id.drawable_name)
    private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
    private val clearButton = itemView.findViewById<ImageButton>(R.id.clear_button)


    override fun bind(data: DrawableProperty<*>)
    {
        itemView.setOnClickListener {
            DrawablePickerDialog(itemView.context, { drawableRes ->  refresh(data, drawableRes)}).show()
        }

        clearButton.setOnClickListener {
            data.setValue(null)
            noDrawable()
        }

        var drawable: Drawable? = null

        data.getValue()?.let {
            drawable = it.constantState?.newDrawable(itemView.resources)
        }

        imageView.setImageDrawable(drawable)
        textview.text = if (data.getValue() == null) "NONE" else ""
        clearButton.show(data.hasValue())
    }

    private fun refresh(data: DrawableProperty<*>, drawable: DrawableResource) {
        if (data.hasValue()) {
            textview.text = drawable.name
            textview.maxWidth
            data.setValue(ResUtils.getDrawable(itemView.context, drawable.resId))

            var drawable: Drawable? = null

            data.getValue()?.let {
                drawable = it.constantState?.newDrawable(itemView.resources)
            }

            imageView.setImageDrawable(drawable)
        }
        else
        {
            noDrawable()
        }
    }

    private fun noDrawable()
    {
        textview.text = "NONE"
        imageView.setImageResource(0)
        clearButton.hide()
    }
}