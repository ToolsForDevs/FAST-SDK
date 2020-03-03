package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.AutoCompleteTextView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty


internal class AutoCompleteTextViewProperties : ViewPropertyHolder(AutoCompleteTextView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, StringProperty(AutoCompleteTextView::getCompletionHint, AutoCompleteTextView::setCompletionHint))
        add(PropertyCategory.COMMON, IdProperty(AutoCompleteTextView::getDropDownAnchor, AutoCompleteTextView::setDropDownAnchor))
        add(PropertyCategory.COMMON, ColorDrawableProperty(AutoCompleteTextView::getDropDownBackground, AutoCompleteTextView::setDropDownBackgroundDrawable))
        // ToDo can set MATCH_PARENT or WRAP_CONTENT add(PropertyCategory.COMMON,
        DimensionIntRangeProperty(AutoCompleteTextView::getDropDownHeight, AutoCompleteTextView::setDropDownHeight, max = screenHeight)
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(AutoCompleteTextView::getDropDownHorizontalOffset, AutoCompleteTextView::setDropDownHorizontalOffset, max = dp(100)))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(AutoCompleteTextView::getDropDownVerticalOffset, AutoCompleteTextView::setDropDownVerticalOffset, max = dp(100)))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(AutoCompleteTextView::getDropDownWidth, AutoCompleteTextView::setDropDownWidth, screenWidth))
        // setInputMethodMode Q
        add(PropertyCategory.COMMON, ListSelectionProperty())
        add(PropertyCategory.COMMON, StringProperty(AutoCompleteTextView::getText, AutoCompleteTextView::setText))
        add(PropertyCategory.COMMON, IntRangeProperty(AutoCompleteTextView::getThreshold, AutoCompleteTextView::setThreshold, max = 15))
    }

    private class ListSelectionProperty : IntRangeProperty<AutoCompleteTextView>(AutoCompleteTextView::getListSelection, AutoCompleteTextView::setListSelection)
    {
        override fun getMaximum(): Int
        {
            if (view.adapter != null) return view.adapter.count - 1

            return 0
        }
    }
}