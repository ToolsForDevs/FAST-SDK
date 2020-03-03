package com.toolsfordevs.fast.plugins.viewinspector.ext

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.CoreDevice
import com.toolsfordevs.fast.core.util.Dimens

@Keep
abstract class ViewPropertyHolder(val viewClass:Class<*>)
{
    protected val screenWidth: Int
        get() = CoreDevice.screenWidthPx

    protected val screenHeight
        get() = CoreDevice.screenHeightPx

    internal val map:HashMap<String, ArrayList<ViewProperty<*, *>>> = hashMapOf()

    fun add(category: String, property: ViewProperty<*, *>)
    {
        var list = map.get(category)

        if (list == null)
        {
            list = arrayListOf()
            map.put(category, list)
        }

        list.add(property)
    }

    fun getProperties():Map<String, List<ViewProperty<*, *>>>
    {
        return map
    }

    protected fun dp(value:Int):Int
    {
        return Dimens.dp(value)
    }

    protected fun dp(value:Float):Float
    {
        return Dimens.dpF(value)
    }

    protected fun sp(value:Int):Int
    {
        return Dimens.sp(value)
    }

    protected fun sp(value:Float):Float
    {
        return Dimens.spF(value)
    }
}