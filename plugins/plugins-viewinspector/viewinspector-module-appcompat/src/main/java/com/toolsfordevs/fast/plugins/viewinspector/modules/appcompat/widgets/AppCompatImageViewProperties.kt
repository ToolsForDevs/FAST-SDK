package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatImageView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatImageViewProperties: ViewPropertyHolder(AppCompatImageView::class.java)
{
    init {
        // setBackgroundDrawable is in View
        // setImageDrawable is in ImageView
    }
}