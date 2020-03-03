package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatMultiAutoCompleteTextViewProperties: ViewPropertyHolder(AppCompatMultiAutoCompleteTextView::class.java)
{
    init
    {
         // setBackgroundDrawable is in View
         // setDropDownBackgroundResource is in AutoCompleteTextView
         // setTextAppearance is in TextView
    }
}