package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import kotlin.reflect.KMutableProperty1

class MarginProperty(private val propertyM: KMutableProperty1<ViewGroup.MarginLayoutParams, Int>, max:Int):
        DimensionIntRangeProperty<View>(max = max)
{
    override fun getValue(): Int {

        val layoutParams = view.layoutParams

        if (layoutParams is ViewGroup.MarginLayoutParams)
            return propertyM.get(layoutParams)

        return 0
    }

    override fun setValue(value: Int?):Boolean {
        val layoutParams = view.layoutParams

        if (layoutParams is ViewGroup.MarginLayoutParams)
        {
            propertyM.set(layoutParams, value ?: 0)
            view.layoutParams = layoutParams
        }

        return true
    }
}