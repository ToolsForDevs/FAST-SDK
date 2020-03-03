package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatEditText
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatEditTextProperties: ViewPropertyHolder(AppCompatEditText::class.java)
{
    init {
        // setBackgroundDrawable is in View
        // setTextAppearance is in TextView
    }
}