package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.AppCompatCheckedTextView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder


internal class AppCompatCheckedTextViewProperties: ViewPropertyHolder(AppCompatCheckedTextView::class.java)
{
    init
    {
         // setCheckMarkDrawable is in View
         // setTextAppearance is in CheckedTextView
    }
}