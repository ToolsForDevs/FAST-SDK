package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatAutoCompleteTextViewProperties: ViewPropertyHolder(AppCompatAutoCompleteTextView::class.java)
{
    init
    {
         // setBackgroundDrawable is in View
         // setDropDownBackgroundResource is in AutoCompleteTextView
         // setTextAppearance is in TextView
    }
}