package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatButton
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatButtonProperties: ViewPropertyHolder(AppCompatButton::class.java)
{
    init
    {
         // setBackgroundDrawable is in View
         // setSupportAllCaps no getter and is in fact, completely useless since it calls the super implentation
       // add(PropertyCategory.COMMON, BooleanProperty(TextView::isAllCaps, AppCompatButton::setSupportAllCaps).apply { newApi = "28" })
        // setTextAppearance is in TextView
         // setTextSize is in TextView
    }
}