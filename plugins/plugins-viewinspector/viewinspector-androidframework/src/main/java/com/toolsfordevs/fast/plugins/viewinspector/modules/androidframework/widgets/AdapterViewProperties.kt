package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.Adapter
import android.widget.AdapterView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.IntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory


internal class AdapterViewProperties: ViewPropertyHolder(AdapterView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, SelectionProperty())
        // setFocusable is in View
        // setFocusableInTouchMode is in View
    }

    private class SelectionProperty : IntRangeProperty<AdapterView<Adapter>>(AdapterView<Adapter>::getSelectedItemPosition, AdapterView<Adapter>::setSelection)
    {
        override fun getMaximum(): Int
        {
            return view.count - 1
        }
    }
}