package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KMutableProperty1

@Keep
class LayoutParamWHProperty<T: View>(name:String, private val propertyM: KMutableProperty1<ViewGroup.LayoutParams, Int>, max:Int) : IntRangeProperty<T>(max = max)
{
    init {
        this.name = name
    }
    override fun getValue(): Int? {
        val layoutParams = view.layoutParams

        return propertyM.get(layoutParams)
    }

    override fun setValue(value: Int?):Boolean {
        val layoutParams = view.layoutParams

        propertyM.set(layoutParams, value ?: 0)
        view.layoutParams = layoutParams

        return true
    }
}

