package com.toolsfordevs.fast.modules.resources

import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class TypedResource(val type: String, val name: String, resId: Int = -1) : Comparable<TypedResource>
{
    companion object
    {
        const val COLOR = "color"
        const val DIMEN = "dimen"
        const val DRAWABLE = "drawable"
        const val STRING = "string"
    }

    private var _resId: Int = resId

    open val resId: Int
        get()
        {
            if (_resId <= 0)
                _resId = AppInstance.get().resources.getIdentifier(name, type, AppInstance.get().packageName)

            return _resId
        }

    override fun compareTo(other: TypedResource): Int
    {
        return name.compareTo(other.name, true)
    }
}