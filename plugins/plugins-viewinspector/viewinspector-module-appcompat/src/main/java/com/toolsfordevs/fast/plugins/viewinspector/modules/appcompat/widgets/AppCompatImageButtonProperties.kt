package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatImageButton
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatImageButtonProperties: ViewPropertyHolder(AppCompatImageButton::class.java)
{
    init {
        // setBackgroundDrawable is in View
        // setImageDrawable is in ImageView
    }
}