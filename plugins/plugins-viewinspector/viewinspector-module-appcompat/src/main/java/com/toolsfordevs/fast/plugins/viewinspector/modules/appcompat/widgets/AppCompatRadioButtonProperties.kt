package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatRadioButton
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatRadioButtonProperties: ViewPropertyHolder(AppCompatRadioButton::class.java)
{
    init {
        // setBackgroundDrawable is in View
        // setButtonDrawable is in CompoundButton
    }
}